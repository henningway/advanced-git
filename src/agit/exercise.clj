(ns agit.exercise
  (:require
   [agit.git :as git]
   [agit.util :as util]
   [agit.cli :as cli]
   [agit.fs :as fs]))

(defn exercise-stash! [dir]
  (git/init! dir)
  (spit (str dir "/hello.txt") "hello")
  (git/add! dir)
  (git/commit! dir "added hello.txt")
  (spit (str dir "/bye.txt") "bye")
  (git/add! dir)
  (git/stash! dir))

(defn exercise-bisect! [dir]
  (git/clone! dir "git@github.com:henningway/HTML5-Asteroids.git"))

(def exercises
  [{:key 1 :title "bisect" :handler exercise-bisect!}
   {:key 2 :title "stash" :handler exercise-stash!}
   {:key 3 :title "reflog" :handler (fn [dir] nil)}
   {:key 4 :title "reset" :handler (fn [dir] nil)}])

(defn resolve
  "Provides full exercise given a (string) key."
  [key]
  (first (filter #(= (util/parse-int key) (:key %)) exercises)))

(defn is-legal-key?
  "Checks if given string can be used as input to the resolve function to get a full exercise."
  [key]
  (and (util/parsable-to-int? key) (not (nil? (resolve key)))))

(defn slug
  "Provides a slug of the exercise using they key and title properties. Useful for naming the exercise's directory."
  [exercise]
  (str "0" (:key exercise) "-" (:title exercise)))

(defn check-key!
  "Checks if given (string) key is legal. If not, informs the user and returns nil, otherwise passes the value through."
  [key]
  (if (is-legal-key? key)
    key
    (println "That is not what I expected. Please try again.")))

(defn format
  "Formats exercise for output during prompting."
  [exercise]
  (str (:key exercise) ") " (:title exercise)))

(defn read-key!
  "Uses read-line to get an exercise key from the user and returns either the chosen value if legal or `nil` if
   validation fails. This function is supposed to be pluggable into cli/read-input!."
  []
  (let [value (read-line)]
    (if (is-legal-key? value)
      value
      nil)))

(defn prompt-key!
  "Prompts user to pick one of the (listed) exercises."
  []
  (println "Choose an exercise...")
  (doseq [exercise exercises] (println (format exercise)))
  (cli/prompt-until-valid! #(read-key!)))

(defn init!
  "Runs given exercise by initializing a directory with the exercises' slug and running the handler function inside."
  [exercise]
  (some-> (fs/init-dir! (slug exercise))
          ((:handler exercise))))

(defn remove!
  "Removes the exercise by deleting its directory."
  [exercise] 
  (fs/remove-dir! (slug exercise)))

(comment
  (resolve 1))
