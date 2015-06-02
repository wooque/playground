(ns lang.core
  (:use lang.jdbc)
  (:use [clojure.java.io :only [delete-file]])
  (:gen-class))

(def insertdata {:name "Java"
                 :desc "Bla"
                 :num 12445
                 :percent 235352.756})

(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "lang.db"})

(defn create-db []
  (try (db-do-commands db
                       (create-table-ddl :lang
                                         [:id :integer "PRIMARY KEY" "NOT NULL"]
                                         [:name :text]
                                         [:desc :text]
                                         [:num :integer]
                                         [:percent :real]))
       (catch Exception e (println e))))

(defn test-db []
  (with-db-transaction [tc db]
    (dotimes [_ 100000]
      (insert! tc :lang insertdata :transaction? false))))

(defn -main
  [& args]
  (delete-file "lang.db" true)
  (create-db)
  (time (test-db)))
