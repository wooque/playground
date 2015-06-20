(ns lang.core
  (:use clojure.java.jdbc)
  (:gen-class))

(def db
  {:classname   "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname     "bla"})

(def prep "INSERT INTO lang (name, dsc, num, percent) VALUES (?, ?, ?, ?)")
(def data ["Java" "Bla" 12445 235352.756])

(defn test-db []
  (with-db-transaction [tx db]
    (let [prep-stmt (prepare-statement (db-find-connection tx) prep)]
      (dotimes [_ 1e5]
        (db-do-prepared tx false prep-stmt data)))))

(defn test-db-conc []
  (->> test-db
       (repeat 4)
       (pmap #(%))
       (doall)))

(defn -main
  [& args]
  (time (test-db-conc))
  (shutdown-agents))
