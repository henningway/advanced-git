(ns agit.core (:require [agit.exercise :as exercise]))

(defn -main [& _args]
  (some-> (exercise/prompt-key!)
          (exercise/check-key!)
          (exercise/resolve) 
          (exercise/run!)))