package main

import (
    "fmt"
    "time"
    "math/rand"
)

type Params struct {
    key int
    value int
}

func main() {
    rand.Seed(time.Now().UTC().UnixNano())

    messages := make(chan Params, 1)
    check := make(chan int)
    quit := make(chan string)

    start := time.Now()
    go func() {

        myMap := make(map[int]int)
        for j := 0; j < 1000; j++ {
            for i := 0; i < 1000; i++ {
                param := <-messages
                myMap[param.key] = param.value
            }
            check <- 1
        }

        quit <- "quit"
    }()

    for j := 0; j < 1000; j++ {
        for i := 0; i < 1000; i++ {
            go func() {
                messages<-Params{rand.Intn(1000000), rand.Intn(1000000)}
            }()
        }
        <-check
    }
    <-quit
    total := time.Since(start)
    fmt.Println(total)
}
