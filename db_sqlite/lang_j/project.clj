(defproject lang "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0-RC1"]
                 ;[org.clojure/java.jdbc "0.3.7"]
                 [org.xerial/sqlite-jdbc "3.8.10.1"]]
  
  :global-vars {*warn-on-reflection* true}
  :main lang.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
