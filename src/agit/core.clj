(ns agit.core (:require [agit.exercise :as ex]))

(defn -main [& _args]
  (some-> (ex/prompt-key!)
          (ex/check-key!)
          (ex/resolve) 
          (ex/init!)))

(defn reset
  "Removes all exercises by deleting their directories."
  []
  (doall (map ex/remove! ex/exercises)))