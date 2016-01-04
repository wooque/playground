package main

import (
	"crypto/sha512"
    "fmt"
	"log"
	"net/http"
    "strconv"
	"time"
)

type RateLimitService struct {
	in    chan int
	out   chan int
	reset chan int
	curr  map[int]int
	max   map[int]int
}

type LoginMsg struct {
	username int
	password string
}

type LoginService struct {
	in  chan LoginMsg
	out chan int
    users map[int][64]byte
}

type Services struct {
	rate_limit RateLimitService
	login      LoginService
}

var services = Services{
	rate_limit: RateLimitService{
		in:    make(chan int),
		out:   make(chan int),
		reset: make(chan int),
		curr:  map[int]int{12: 1, 123: 1000, 1234: 10000, 5678: 20000},
		max:   map[int]int{12: 1, 123: 1000, 1234: 10000, 5678: 20000}},
	login: LoginService{
		in:  make(chan LoginMsg),
		out: make(chan int),
        users: map[int][64]byte{5678: {54, 39, 144, 154, 41, 195, 19, 129, 160, 113, 236, 39, 247, 201, 202, 151, 114, 97, 130, 174, 210, 154, 125, 221, 46, 84, 53, 51, 34, 207, 179, 10, 187, 158, 58, 109, 242, 172, 44, 32, 254, 35, 67, 99, 17, 214, 120, 86, 77, 12, 141, 48, 89, 48, 87, 95, 96, 226, 211, 208, 72, 24, 77, 121}}}}

func handler(w http.ResponseWriter, r *http.Request) {
    if r.URL.RawQuery == "" {
		w.WriteHeader(http.StatusNotFound)
		fmt.Fprint(w, "No user id!")
		return
    }
    params := r.URL.Query()
    id, err := strconv.Atoi(params["user"][0])
	if err != nil {
		w.WriteHeader(http.StatusNotFound)
		fmt.Fprint(w, "Bad user id!")
		return
	}
    user := int(id)

    pass := params["pass"][0]
    services.login.in <- LoginMsg{username: user, password: pass}
    login_res := <-services.login.out
    if login_res == 0 {
        w.WriteHeader(http.StatusNotFound)
        fmt.Fprint(w, "Bad credentials")
        return
    }

	services.rate_limit.in <- user
	limit := <-services.rate_limit.out
	switch limit {
	case -1:
		w.WriteHeader(http.StatusNotFound)
		fmt.Fprint(w, "User not found!")
	case 0:
		w.WriteHeader(http.StatusServiceUnavailable)
		fmt.Fprint(w, "Rate limit reached!")
	default:
		fmt.Fprint(w, "Hello! ", limit)
	}
}

func rate_limit() {
	curr := services.rate_limit.curr
	for {
		select {
		case id := <-services.rate_limit.in:
			limit, ok := curr[id]
			if ok == false {
				services.rate_limit.out <- -1
			} else {
				if limit > 0 {
					curr[id]--
				}
				services.rate_limit.out <- curr[id]
			}
		case <-services.rate_limit.reset:
			for k, _ := range curr {
				curr[k] = services.rate_limit.max[k]
			}
		}
	}
}

func rate_limit_reset() {
	for {
		services.rate_limit.reset <- 1
		time.Sleep(1000 * time.Millisecond)
	}
}

func login() {
    for {
        creds := <-services.login.in
        hashed_pass, ok := services.login.users[creds.username]
        if ok == false {
            services.login.out <- 0
        } else {
            if sha512.Sum512([]byte(creds.password)) == hashed_pass {
                services.login.out <- 1
            } else {
                services.login.out <- 0
            }
        }
    }
}

func main() {
	go login()
    go rate_limit()
	go rate_limit_reset()
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		handler(w, r)
	})
	log.Fatal(http.ListenAndServe(":8080", nil))
}
