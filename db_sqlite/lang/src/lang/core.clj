(ns lang.core
  (:use clojure.java.jdbc)
  (:use [clojure.java.io :only [delete-file]])
  (:gen-class))

(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "lang.db"})

(defn create-db []
  (db-do-commands db (create-table-ddl :lang
                                       [:id :integer "PRIMARY KEY" "NOT NULL"]
                                       [:name :text]
                                       [:desc :text]
                                       [:num :integer]
                                       [:percent :real])))

(def prep "INSERT INTO lang (name, desc, num, percent) VALUES (?, ?, ?, ?)")
(def data ["Java" "Bla" "12445" "235352.756"])

(defn test-db []
  (with-db-transaction [tc db]
    (let [prep-stmt (prepare-statement (db-find-connection tc) prep)]
      (dotimes [_ 1e6]
        (db-do-prepared tc false prep-stmt data)))))

(defn -main
  [& args]
  (delete-file "lang.db" true)
  (create-db)
  (time (test-db)))
