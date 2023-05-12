(ns agit.fs
  (:require
   [babashka.fs :as fs]
   [clojure.java.shell :as sh]
   [agit.cli :as cli]))

(defn- prompt-dir-replacement!
  "Asks user for confirmation to replace given directory."
  [dir]
  (cli/prompt-until-valid! #(cli/confirm! (str "Exercise " dir " already exists. Reset (y/n)?"))))

(defn remove-dir!
  "Force removes directory of given name. Use with care!"
  [dir]
  (sh/sh "rm" "-rf" dir))

(defn init-dir!
  "Initializes directory of given name, i.e. creates it if it doesn't exists yet, otherwise prompts the user for
   confirmation to replace it. Returns the given directory name or nil if the user declines (supporting threading with
   nil-punning.)"
  [dir] 
  (if (fs/exists? dir)
    (when (prompt-dir-replacement! dir) (do (remove-dir! dir) (init-dir! dir))) ; linter warns of redundant do, but it doesnt work correctly without
    (do (fs/create-dir dir) dir)))