#!/usr/bin/env bb

(require
 '[babashka.fs :as fs]
 '[clojure.java.shell :as sh])

(defn str-is-parsable-to-int [str] 
  (and (> (count str) 0) (every? #(Character/isDigit %) str)))

(defn parse-int [s]
  (Integer. s))

(defn git-init!
  "Runs `git init .` inside given directory."
  [dir]
  (sh/sh "git" "init" "." :dir dir))

(defn git-add!
  "Runs `git add .` inside given directory."
  [dir]
  (sh/sh "git" "add" "." :dir dir))

(defn git-commit!
  "Runs `git commit` inside given directory with given message."
  [dir msg]
  (sh/sh "git" "commit" "-m" msg :dir dir))

(defn git-stash!
  "Runs `git stash` inside given directory."
  [dir]
  (sh/sh "git" "stash" :dir dir))

(defn confirm! [msg]
  (println msg)
  (let [value (read-line)]
    (if (or (= value "y") (= value "n"))
      (= value "y")
      nil)))

(defn read-input! [reader]
  (loop [value (reader)]
    (if (nil? value)
      (recur (do (println "That is not what I expected. Please try again.") (reader)))
      value)))

(defn exercise-stash! [dir]
  (git-init! dir)
  (spit (str dir "/hello.txt") "hello")
  (git-add! dir)
  (git-commit! dir "added hello.txt") 
  (spit (str dir "/bye.txt") "bye")
  (git-add! dir)
  (git-stash! dir))

(def exercises
  [{:key 1 :title "bisect" :handler (fn [dir] nil)}
   {:key 2 :title "stash" :handler exercise-stash!}
   {:key 3 :title "reflog" :handler (fn [dir] nil)}
   {:key 4 :title "reset" :handler (fn [dir] nil)}])

(defn resolve-exercise
  "Provides full exercise given a (string) key."
  [key]
  (first (filter #(= (parse-int key) (:key %)) exercises)))

(defn is-legal-exercise-key? [key]
  (and (str-is-parsable-to-int key) (not (nil? (resolve-exercise key)))))

(defn exercise-slug
  "Provides a slug of the exercise using they key and title properties. Useful for naming the exercise's directory."
  [exercise]
  (str "0" (:key exercise) "-" (:title exercise)))

(defn format-exercise
  "Formats exercise for output during prompting."
  [exercise]
  (str (:key exercise) ") " (:title exercise)))

(defn read-exercise! []
  (let [value (read-line)]
   (if (is-legal-exercise-key? value)
     value
     nil)))

(defn pick-exercise! 
  "Prompts user to pick one of the exercises."
  []
  (println "Choose an exercise...")
  (doseq [exercise exercises]
    (println (format-exercise exercise)))
  (read-input! #(read-exercise!)))

(defn check-exercise-key!
  "Checks if given (string) key is legal. If not, informs the user and returns nil, otherwise passes the value through."
  [key]
  (if (is-legal-exercise-key? key)
    key
    (println "That is not what I expected. Please try again.")))

(defn init-dir! [dir]
  (if (fs/exists? dir)
    (if (read-input! #(confirm! (str "Exercise " dir " already exists. Reset (y/n)?")))
      (do (sh/sh "rm" "-rf" dir) (init-dir! dir))
      nil)
    (do (fs/create-dir dir) dir)))

(defn run-exercise! [exercise]
  (let [dir (exercise-slug exercise)
        handler (:handler exercise)]
    (some-> (init-dir! dir)
            (handler))))

(some-> (pick-exercise!)
        (check-exercise-key!)
        (resolve-exercise) 
        (run-exercise!))

(comment
  (resolve-exercise 1))
