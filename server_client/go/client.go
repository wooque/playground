package main

import (
    "net/http"
    "io/ioutil"
    "fmt"
    "time"
)

func main() {
    
    const N = 5000
    var chans [8]chan int
    start := time.Now()

    for c := 0; c < 8; c++ {
        
        chans[c] = make(chan int)
        
        go func(fin chan int) {
            
            for i := 0; i < N/8; i++ {

                resp, err := http.Get("http://127.0.0.1:8080/")
                if err != nil {
                    fmt.Println("error")
                }
            
                defer resp.Body.Close()
            
                body, err := ioutil.ReadAll(resp.Body)
                if err != nil {
                    fmt.Println("error")
                    fmt.Println(body)
                }
            }
            
            fin<-1
            
        }(chans[c])
    }
    
    for j := 0; j < 8; j++ {
        <-chans[j]
    }
    
    total := time.Since(start)
    fmt.Println((N/(float64)(total/time.Millisecond)) * 1000, "req/s")
}
