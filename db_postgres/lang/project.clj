(defproject lang "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0-RC1"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]]
  
  :global-vars {*warn-on-reflection* true}
  :main lang.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
