(ns tt.core
    (:gen-class)
    (:use org.httpkit.server))
(set! *warn-on-reflection* true)


(defn handler [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello World!"})

(defn async-handler [req]
  (with-channel req channel 
      (send! channel {:status 200
                      :headers {"Content-Type" "text/plain"}
                      :body    "Hello World!"})))


(def routes {"/" handler 
             "/async" async-handler})

(defn router [req]
  (let [handler (routes (:uri req))]
    (if handler 
      (handler req) 
      {:status 404})))

(defn -main[]
    (run-server router {:port 8080}))
