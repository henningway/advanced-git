(ns agit.util)

(defn parsable-to-int?
  "Checks whether given string is parsable to int."
  [str]
  (and (> (count str) 0) (every? #(Character/isDigit %) str)))

(defn parse-int
  "Coerces given string to integer."
  [s]
  (Integer. s))