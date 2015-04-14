package main

import (
    "fmt"
    "strconv"
    "net/http"
    "io/ioutil"
    "time"
)

func counter(idgen chan int) {
    cnt := 0
    for ;; {
        idgen<-cnt
        cnt++;
    }
}

func sender(dlrs chan int) {
    time.Sleep(1000 * time.Millisecond)
    for ;; {
        id := <-dlrs
        resp, err := http.Get("http://127.0.0.1:8081/user/provider?type=dlr&status=delivered&refid=" + strconv.Itoa(id))
        if err != nil {
            fmt.Println("error")
        }
        
        body, err := ioutil.ReadAll(resp.Body)
        if err != nil {
            fmt.Println("error")
            fmt.Println(body)
        }
        resp.Body.Close()
    }
}

func handler(w http.ResponseWriter, r *http.Request, idgen chan int, dlrs chan int) {
    id := <-idgen
    fmt.Fprintf(w, "status=OK;refid=" + strconv.Itoa(id))
    dlrs<-id
}

func main() {
    dlrs := make(chan int, 10000)
    idgen := make(chan int, 10000)
    
    go counter(idgen)
    
    for c := 0; c < 32; c++ {
        go sender(dlrs)
    }
    
    http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
        handler(w, r, idgen, dlrs)
    })
    
    http.ListenAndServe(":8080", nil)
}
