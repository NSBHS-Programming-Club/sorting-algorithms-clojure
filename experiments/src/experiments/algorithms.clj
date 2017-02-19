(ns experiments.algorithms
  (:gen-class))

; Bubble sort
(defn bubble-sort [array]
  (loop [ind 0
         sort-arr array
         swapped false]
    (if (= ind (dec (count array)))
      (if swapped
        (bubble-sort sort-arr)
        sort-arr)
      (if (> (nth sort-arr ind) (nth sort-arr (inc ind)))
        (let
          [temp-arr
            (assoc sort-arr
              ind (sort-arr (inc ind))
              (inc ind) (sort-arr ind))]
          (do
            (println temp-arr)
            (recur (inc ind) temp-arr true)))
        (recur (inc ind) sort-arr swapped)))))

; Merge sort
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

(defn merge-sort [array]
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
                (assoc temp-arr
                  ind
                  (if (= (count (nth temp-arr ind)) 1)
                    (nth (nth temp-arr ind) 0)
                    (sort-two (nth temp-arr ind))))))))))))

; Dijkstra's pathfinding algorithm

; Insert element into an array
(defn insert [item array ind]
  (vec
    (concat
      (subvec array 0 (inc ind))
      [item]
      (subvec array (inc ind)))))

; Finds all arrays with starting point of [element]
(defn find-arrays [paths element]
  ; element => [node cost previous-nodes]
  (loop [ind 0
         links []]
    (if (= ind (count paths)) links
      (let [current-path (nth paths ind)]
        (recur
            (inc ind)
            (if
              (some #{(first element)}
                (vec (butlast current-path)))
              (conj links
                [(if (= (first current-path) (first element))
                   (nth current-path 1)
                   (first current-path))
                 (+ (nth current-path 2) (second element))
                 (conj (nth element 2) (first element))])
              links))))))

; Places an element onto the stack
(defn place-element [stack element]
  (loop [ind 0]
    (if (= ind (count stack))
      (loop [new-ind 0]
        (if (= new-ind (count stack))
          (conj stack element)
          (if (>= (second element) (second (nth stack new-ind)))
            (insert element stack new-ind)
            (recur (inc new-ind)))))
      (if (= (first element) (first (nth stack ind)))
        (if (< (second element) (second (nth stack ind)))
          (assoc stack ind element) stack)
        (recur (inc ind))))))

; Full Dijkstra algorithm
(defn dijkstra [paths start end]
  (loop [stack [[start 0 []]]]
    (if (= (first (first stack)) end)
      (println (last (first stack)))
      (recur
        (let [found-paths (find-arrays paths (first stack))]
          (loop [ind 0
                 new-stack (vec (rest stack))]
            (if (= ind (count found-paths)) new-stack
              (recur
                (inc ind)
                (place-element new-stack (nth found-paths ind))))))))))
