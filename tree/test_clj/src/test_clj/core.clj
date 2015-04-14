(ns test_clj.core
  (:gen-class))

(set! *warn-on-reflection* true)

(deftype Node [^long value left right])

(defn exp ^Node [^long init ^long level] 
    (if (zero? level) 
        nil
        (Node. init 
            (exp init (dec level))
            (exp init (dec level)))))
            
(defn sum ^long [^Node node]
    (if (nil? node)
        0
        (+ (.value node)
           (sum (.left node)) 
           (sum (.right node)))))

(defn -main
  [& args]
  (let [start (System/currentTimeMillis)]
    (doseq [j (range 1 101)]
        (doseq [i (range 1 21)]
            (println i ":" (sum (exp (- 25 i) i)))))
    (println "Time:" (/ (- (System/currentTimeMillis) start) 1000.0))))
