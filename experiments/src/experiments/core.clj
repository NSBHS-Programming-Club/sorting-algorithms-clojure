(ns experiments.core
  (:gen-class)
  (:require [clojure.string :as str]
            [experiments.command-line :as cl]
            [experiments.algorithms :as alg]))

; Generate a random array
(defn random-array [length]
  (loop [arr []]
    (if (= (count arr) length) arr
      (let [rand-val (rand-int length)]
        (if (some #{rand-val} arr)
          (recur arr)
          (recur (conj arr rand-val)))))))

(defn input [string]
  (print string)
  (flush)
  (read-line))

; Initialise arguments
(defn init-args []
  (cl/add-arg "-b" "bubble"
    ["Print the procedure of bubble-sorting,"
     "given an arbitrary amount of arguments."]
    (fn
      ([]
       (alg/bubble-sort
         (random-array 10)))
      ([args]
       (alg/bubble-sort
         (vec
           (for [a (range (count args))]
             (read-string (nth args a))))))))
  (cl/add-arg "-d" "dijkstra"
    ["Prints the shortest path using Dijkstra's algorithm,"
     "given paths, a starting node and an ending node."]
    (fn
      [[start end]]
      (loop [paths []]
        (let [user-in (input "Enter path-start, path-end and distance: ")]
          (if (= user-in "")
            (alg/dijkstra paths (read-string start) (read-string end))
            (recur
              (conj paths
                (vec
                  (let [list-strs (str/split user-in #" ")]
                    (for [a (range (count list-strs))]
                      (read-string (nth list-strs a))))))))))))
  (cl/add-arg "-m" "merge"
    ["Print the procedure of merge-sorting,"
     "given an arbitrary amount of arguments."]
    (fn
      ([]
       (alg/merge-sort
         (random-array 10)))
      ([args]
       (alg/merge-sort
         (vec
           (for [a (range (count args))]
             (read-string (nth args a))))))))
  (cl/add-arg "-r" "random"
    ["Generate a random array."]
    (fn
      ([args]
       (println
         (random-array
           (read-string (nth args 0))))))))

; Main argument
(defn -main [& args]
  (init-args)
  (cl/parse-arg (vec args)))
