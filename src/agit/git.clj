(ns agit.git (:require [clojure.java.shell :as sh]))

(defn init!
  "Runs `git init .` inside given directory."
  [dir]
  (sh/sh "git" "init" "." :dir dir))

(defn add!
  "Runs `git add .` inside given directory."
  [dir]
  (sh/sh "git" "add" "." :dir dir))

(defn commit!
  "Runs `git commit` inside given directory with given message."
  [dir msg]
  (sh/sh "git" "commit" "-m" msg :dir dir))

(defn stash!
  "Runs `git stash` inside given directory."
  [dir]
  (sh/sh "git" "stash" :dir dir))