(ns experiments.command-line
  (:gen-class)
  (:require [clojure.string :as string]))

(def names (atom [["-h" "help"]]))
(def docs (atom [[""]]))
(def funcs (atom [(fn [] ())]))

(defn add-arg [s-name l-name arg-doc func]
  (swap! names conj [s-name l-name])
  (swap! docs conj arg-doc)
  (swap! funcs conj func))

(defn disp-help []
  (println "\nCommands: ")
  (loop [a 1]
    (if (= a (count @names))
      (print "")
      (do
        (println "  "
          (nth (nth @names a) 0) "\b,"
          (nth (nth @names a) 1) "\b:\n   "
          (string/join "\n    "
            (nth @docs a)))
        (println "")
        (recur (+ a 1))))))

(defn parse-arg [args]
  (let [main-arg (nth args 0)
        other-args (subvec args 1)]
    (loop [a 0]
      (if (= a (count @names))
        (println "No match!")
        (if
          (or
            (= main-arg (nth (nth @names a) 0))
            (= main-arg (nth (nth @names a) 1)))
          (if (= a 0)
            (disp-help)
            ((nth @funcs a) other-args))
          (recur (+ a 1)))))))
