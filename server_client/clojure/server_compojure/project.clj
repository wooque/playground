(defproject tt "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.4.0"]
                 [http-kit "2.1.19"]
                 [javax.servlet/servlet-api "2.5"]]
  :main tt.core
  :profiles {:uberjar {:aot :all}})
