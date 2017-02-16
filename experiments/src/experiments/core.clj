(ns experiments.core
  (:gen-class)
  (:require [experiments.command-line :as cl]))

(defn bubble-sort [array]
  (loop [ind 0
         sort-arr array
         swaps? 0]
    (if (= ind (dec (count array)))
      (if (not= swaps? 0)
        (bubble-sort sort-arr))
      (if (> (nth sort-arr ind) (nth sort-arr (inc ind)))
        (let
          [temp-arr
            (vec
              (concat
                (subvec sort-arr 0 ind)
                [(nth sort-arr (inc ind))]
                [(nth sort-arr ind)]
                (subvec sort-arr (+ ind 2))))]
          (do
            (println temp-arr)
            (recur (inc ind) temp-arr (inc swaps?))))
        (recur (inc ind) sort-arr swaps?)))))

(defn merge-sort [array]
  (defn split-array [arr]
    (loop [orig arr final []]
      (if (< (count orig) 2)
        (if (= (count orig) 1)
          (conj final orig)
          final)
        (recur
          (subvec orig 2)
          (conj final (subvec orig 0 2))))))
  (defn sort-two [[a b]]
     (loop [arr-a a
            arr-b b
            final []]
       (if
         (or
           (empty? arr-a)
           (empty? arr-b))
         (vec
           (concat
             final
             (if (empty? arr-a) arr-b arr-a)))
         (if (> (first arr-a) (first arr-b))
           (recur
             arr-a
             (vec (rest arr-b))
             (conj final (first arr-b)))
           (recur
             (vec (rest arr-a))
             arr-b
             (conj final (first arr-a)))))))
  (loop
    [sort-arr
      (split-array
        (vec
          (for [a (range (count array))] [(nth array a)])))]
    (if (= (count sort-arr) 1)
      (println (sort-two (nth sort-arr 0)))
      (recur
        (split-array
          (loop [ind 0
                 temp-arr sort-arr]
            (println temp-arr)
            (if (= ind (count temp-arr)) temp-arr
              (recur
                (inc ind)
                (vec
                    (concat
                      (subvec temp-arr 0 ind)
                      [(if (= (count (nth temp-arr ind)) 1)
                         (nth (nth temp-arr ind) 0)
                         (sort-two (nth temp-arr ind)))]
                      (subvec temp-arr (inc ind))))))))))))

(defn gen-random-array [length]
  (loop [arr []]
    (if (= (count arr) length) arr
      (let [rand-val (rand-int length)]
        (if (some #{rand-val} arr)
          (recur arr)
          (recur (conj arr rand-val)))))))

(defn init-args []
  (cl/add-arg "-b" "bubble"
    ["Print the procedure of bubble-sorting,"
     "given an arbitrary amount of arguments."]
    (fn
      ([]
       (bubble-sort
         (gen-random-array 10)))
      ([args]
       (bubble-sort
         (vec
           (for [a (range (count args))]
             (read-string (nth args a))))))))
  (cl/add-arg "-m" "merge"
    ["Print the procedure of merge-sorting,"
     "given an arbitrary amount of arguments."]
    (fn
      ([]
       (merge-sort
         (gen-random-array 10)))
      ([args]
       (merge-sort
         (vec
           (for [a (range (count args))]
             (read-string (nth args a))))))))
  (cl/add-arg "-r" "random"
    ["Generate a random array."]
    (fn
      ([args]
       (println
         (gen-random-array
           (read-string (nth args 0))))))))

(defn -main [& args]
  (init-args)
  (cl/parse-arg (vec args)))
