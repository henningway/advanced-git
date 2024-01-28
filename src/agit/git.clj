(ns agit.git (:require [clojure.java.shell :as sh]))

(defn init!
  "Runs `git init .` inside given directory."
  [dir]
  (sh/sh "git" "init" "." :dir dir))

(defn add!
  "Runs `git add .` inside given directory."
  ([dir]
   (sh/sh "git" "add" "." :dir dir))
  ([dir file]
   (sh/sh "git" "add" file :dir dir)))

(defn commit!
  "Runs `git commit` inside given directory with given message."
  [dir msg]
  (sh/sh "git" "commit" "-m" msg :dir dir))

(defn stash!
  "Runs `git stash` inside given directory."
  ([dir]
   (sh/sh "git" "stash" :dir dir))
  ([dir msg]
   (sh/sh "git" "stash" "-m" msg :dir dir)))

(defn clone!
  "Runs `git clone` inside given directory with given repository."
  [dir repo]
  (sh/sh "git" "clone" repo "." :dir dir))

(defn reset-head!
  "Runs `git reset HEAD~n` inside given directory where n is the given number of commits to go backwards."
  [dir n hard]
  (sh/sh "git" "reset" (if hard "--hard" "") (str "HEAD~" n) :dir dir))

(defn remove-remote!
  "Runs `git remote remove` inside given directory with given remote name."
  [dir remote]
  (sh/sh "git" "remote" "remove" remote :dir dir))

(defn remove-branch!
  "Runs `git branch -D` inside given directory with given branch name."
  [dir branch]
  (sh/sh "git" "branch" "-D" branch :dir dir))

(defn checkout!
  "Runs `git checkout` inside given directory with given branch name."
  [dir branch]
  (sh/sh "git" "checkout" branch :dir dir))