package main

import (
    "fmt"
    "net/http"
    "log"
)

func handler(w http.ResponseWriter, r *http.Request, c chan int) {
    fmt.Fprintln(w, "Hello!")
    c<-1
}

func counter(c chan int) {
    var count int
    for {
        <-c
        count++
        if count % 10000 == 0 {
            fmt.Println("Received 10000")
        }
    }
}

func main() {
    c := make(chan int)
    go counter(c);
    http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
        handler(w, r, c)
    })
    log.Fatal(http.ListenAndServe(":8080", nil))
}
