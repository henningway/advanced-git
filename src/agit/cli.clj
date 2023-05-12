(ns agit.cli)

(defn prompt-until-valid!
  "Runs given prompt function in a loop until its return value is not `nil`. This is useful to make sure that the user
   provides a legal value. This requires that the prompt function performs validation and returns nil if the value is
   invalid."
  [prompt] 
  (loop [value (prompt)]
    (if (nil? value)
      (recur (do (println "That is not what I expected. Please try again.") (prompt)))
      value)))

(defn confirm! [msg]
  (println msg)
  (let [value (read-line)]
    (if (or (= value "y") (= value "n"))
      (= value "y")
      nil)))

