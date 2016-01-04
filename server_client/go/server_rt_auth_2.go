package main

import (
	"fmt"
	"log"
	"net/http"
	"strconv"
	"sync/atomic"
	"time"
)

func handler(w http.ResponseWriter, r *http.Request, limits map[int]*int32, c chan int) {
	id, err := strconv.Atoi(r.URL.Path[1:])
	if err != nil {
		w.WriteHeader(http.StatusNotFound)
		fmt.Fprint(w, "No user id!")
		return
	}
	c <- id
	limitptr, ok := limits[id]
	if ok == false {
		w.WriteHeader(http.StatusNotFound)
		fmt.Fprint(w, "User not found!")
	} else {
		limit := atomic.LoadInt32(limitptr)
		if limit == 0 {
			w.WriteHeader(http.StatusServiceUnavailable)
			fmt.Fprint(w, "Rate limit reached!")
		} else {
			atomic.AddInt32(limitptr, -1)
			fmt.Fprint(w, "Hello! ", limit-1)
		}
	}
}

func counter(l map[int]int32, c chan int) {
	for {
		id := <-c
		l[id]++
	}
}

func timer(limits map[int]*int32, max_limits map[int]int32) {
	for {
		for k, v := range limits {
			atomic.StoreInt32(v, max_limits[k])
		}
		time.Sleep(1000 * time.Millisecond)
	}
}

func main() {
	ml := map[int]int32{12: 1, 123: 1000, 1234: 10000, 5678: 20000}
	al := map[int]*int32{12: new(int32), 123: new(int32), 1234: new(int32), 5678: new(int32)}
	for k, _ := range ml {
		*al[k] = ml[k]
	}
	cm := make(map[int]int32)
	for k, _ := range ml {
		cm[k] = 0
	}
	c := make(chan int)
	go counter(cm, c)
	go timer(al, ml)
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		handler(w, r, al, c)
	})
	log.Fatal(http.ListenAndServe(":8080", nil))
}
