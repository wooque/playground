(defproject tt "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                [http-kit "2.1.19"]]
  :main tt.core
  :profiles {:uberjar {:aot :all}})
