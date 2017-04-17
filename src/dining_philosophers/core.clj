(ns dining-philosophers.core)


(def table (atom {:forks [0 0 0 0 0]
                  :eaten [0 0 0 0 0]}))

(defn run? []
  ;; I didn't find any better solutions.
  (< (nth (@table :eaten) 0) 20))

(defn think [philosopher]
  ;;(println "philosopher" philosopher "is thinking..")
  (Thread/sleep (rand-int 50))
  ;;(println "philosopher" philosopher "is done thinking!")
  )

(defn eat! [philosopher]
  ;;(println "philosopher" philosopher "is eating..")
  (swap! table update-in [:eaten philosopher] inc)
  (Thread/sleep (rand-int 30))
  ;;(println "philosopher" philosopher "is done eating!")
  )

(defn forks-update! [position status]
  (swap! table assoc-in [:forks position] status))

(defn grab-fork [fork-position]
  (if (= 0 (nth (@table :forks) fork-position))
    (do
      (forks-update! fork-position 1)
      true)
    false))

(defn release-forks [a-fork b-fork]
  (forks-update! a-fork 0)
  (forks-update! b-fork 0))

(defn grab-forks-and-eat [position]
  (let [x (mod (+ position 0) 5)
        y (mod (+ position 1) 5)]
    (if (and (grab-fork x)
             (grab-fork y))
      (eat! position))
    (release-forks x y)))

(defn philosopher [position]
  (while (run?)
    (think position)
    (grab-forks-and-eat position)))

(defn print-results []
  (while (run?)
    (Thread/sleep 50)
      (println "philosopher's bellies:"
              (nth (@table :eaten) 0)
              (nth (@table :eaten) 1)
              (nth (@table :eaten) 2)
              (nth (@table :eaten) 3)
              (nth (@table :eaten) 4))))

  (defn main- []
    (future (philosopher 0))
    (future (philosopher 1))
    (future (philosopher 2))
    (future (philosopher 3))
    (future (philosopher 4))
    (future (print-results)))

(main-)
