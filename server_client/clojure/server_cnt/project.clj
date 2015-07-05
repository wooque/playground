(defproject tt "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.18"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]]
  :main tt.core
  :profiles {:uberjar {:aot :all}})
