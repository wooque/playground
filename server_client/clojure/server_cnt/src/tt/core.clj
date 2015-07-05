(ns tt.core
  (:gen-class)
  (:require [org.httpkit.server :refer [with-channel websocket? on-receive send! run-server]])
  (:require [clojure.core.async :refer [chan >! >!! <! <!! go close!]]))

(set! *warn-on-reflection* true)

(def cnt (chan 1000))

(defn counter []
  (go 
    (loop [i 0] 
      (if (= (<! cnt) 1)
        (if (= i 10000)
          (do (println "10000")
              (recur 0))
          (recur (inc i)))
        (println "finished")))))

(defn async-handler [ring-request]
  (with-channel ring-request channel 
    (if (websocket? channel)
      (on-receive channel (fn [data]
                            (send! channel data)))
      (do (>!! cnt 1) 
          (send! channel {:status 200
                          :body   "Hello World!"})))))

(defn -main[]
  (counter)
  (run-server async-handler {:port 8080}))
