(ns test_vec_clj.core
  (:gen-class))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn exp ^long [^ints tree ^long free ^long init ^long level]
  
  (if (zero? level)
    -1
    (let [left (exp tree free init (dec level))
          right (exp tree 
                     (if (= -1 left)
                       free 
                       (+ 3 left)) 
                     init 
                     (dec level))
          curr-free (if (= -1 right)
                      free
                      (+ 3 right))]
      
      (aset tree curr-free init)
      (aset tree (inc curr-free) left)
      (aset tree (+ 2 curr-free) right)
      curr-free)))
            
(defn sum ^long [^ints tree ^long index]

  (if (= -1 index)
    0
    (let [value (aget tree index)
          left (aget tree (inc index))
          right (aget tree (+ 2 index))]

      (+ value (sum tree left) (sum tree right)))))

(defn -main [& args]
  
  (let [start (System/currentTimeMillis)
        tree (int-array (* 3 (bit-shift-left 1 20)))]
    
    (doseq [j (range 1 101)]
      (doseq [i (range 1 21)]
        
        (let [ind (exp tree 0 (- 25 i) i)]
          (println i ":" (sum tree ind)))))
    
    (println "Time:" (/ (- (System/currentTimeMillis) start) 1000.0) "s")))
