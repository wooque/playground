(ns tt.core
    (:gen-class)
    (:use org.httpkit.server))
(set! *warn-on-reflection* true)

(defn async-handler [ring-request]
  (with-channel ring-request channel 
    (if (websocket? channel)
      (on-receive channel (fn [data]
                            (send! channel data)))
      (send! channel {:status 200
                      :headers {"Content-Type" "text/plain"}
                      :body    "Long polling?"}))))

(defn -main[]
    (run-server async-handler {:port 8080}))
