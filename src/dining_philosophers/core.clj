(ns dining-philosophers.core)


(def forks (atom [0 0 0 0 0]))
(def eaten (atom [0 0 0 0 0]))

(defn run? []
  ;; I didn't find any better solutions.
  (< (nth @eaten 0) 20))

(defn think [philosopher]
  ;;(println "philosopher" philosopher "is thinking..")
  (Thread/sleep (rand-int 50))
  ;;(println "philosopher" philosopher "is done thinking!")
  )

(defn eat! [philosopher]
  ;;(println "philosopher" philosopher "is eating..")
  (swap! eaten assoc philosopher (inc (nth @eaten philosopher)))
  (Thread/sleep (rand-int 30))
  ;;(println "philosopher" philosopher "is done eating!")
  )

(defn grab-fork! [fork-position]
  (if (= 0 (nth @forks fork-position))
    (do
      (swap! forks assoc fork-position 1)
      true)
    false))

(defn release-forks! [x y]
  (swap! forks assoc x 0)
  (swap! forks assoc y 0))

(defn grab-forks-and-eat [position]
  (let [x (mod (+ position 0) 5)
        y (mod (+ position 1) 5)]
    (if (and (grab-fork! x)
             (grab-fork! y))
      (eat! position))
    (release-forks! x y)))

(defn philosopher [position]
  (while (run?)
    (think position)
    (grab-forks-and-eat position)))

(defn print-results []
  (while (run?)
    (Thread/sleep 50)
      (println "philosopher's bellies:"
              (nth @eaten 0)
              (nth @eaten 1)
              (nth @eaten 2)
              (nth @eaten 3)
              (nth @eaten 4))))

  (defn main- []
    (future (philosopher 0))
    (future (philosopher 1))
    (future (philosopher 2))
    (future (philosopher 3))
    (future (philosopher 4))
    (future (print-results)))

(main-)
