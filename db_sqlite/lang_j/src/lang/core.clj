(ns lang.core
  (:import [java.sql DriverManager Connection Statement PreparedStatement])
  (:use [clojure.java.io :only [delete-file]])
  (:gen-class))

(def cq (str "CREATE TABLE lang "
             "(id integer primary key not null,"
             "name text,"
             "desc text,"
             "num integer,"
             "percent real)"))

(def preq (str "INSERT INTO lang (name, desc, num, percent) "
               "VALUES (?, ?, ?, ?)"))

(defn create-db [create-query]
  (try 
    (Class/forName "org.sqlite.JDBC")
    (let [conn (DriverManager/getConnection "jdbc:sqlite:lang.db")
          stmt (.createStatement conn)]
      (.executeUpdate stmt create-query)
      (.close stmt)
      conn)
    (catch Exception e (println e))))

(defn test-db [^Connection conn preq]
  (try 
    (let [pres (.prepareStatement conn preq)]
      (. conn setAutoCommit false)
      (dotimes [i 1000000]
        (.setString pres 1 (str "Java" i))
        (.setString pres 2 (str "Bla" i))
        (.setInt pres 3 i)
        (.setDouble pres 4 (* i 0.27))
        (.executeUpdate pres))
      (.close pres)
      (.commit conn)
      (.close conn))
    (catch Exception e (println e))))

(defn -main
  [& args]
  (delete-file "lang.db" true)
  (let [db (create-db cq)]
    (time (test-db db preq))))
