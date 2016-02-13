(ns tt.core
    (:gen-class)
    (:use [compojure.route :only [not-found]]
          [compojure.handler :only [site]]
          [compojure.core :only [defroutes GET]]
          org.httpkit.server))

(set! *warn-on-reflection* true)

(defn hw-handler [req] 
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body    "Hello world!"})

(defn async-handler [req]
  (with-channel req channel
      (send! channel {:status 200
                      :headers {"Content-Type" "text/plain"}
                      :body    "Hello world!"})))

(defroutes all-routes
    (GET "/" [] hw-handler)
    (GET "/async" [] async-handler)
    (not-found "<p>Page not found.</p>")) 

(defn -main[]
    (run-server (site #'all-routes) {:port 8080}))
