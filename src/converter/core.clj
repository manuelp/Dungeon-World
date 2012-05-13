(ns converter.core
  (:use clojure.xml
        [hiccup.core :only [html]]
        [hiccup.page :only [html5]])
  (:require [clojure.string :as s])
  (:import java.io.File)
  (:gen-class))

(defn files-list
  []
  (filter #(re-seq #".*\.xml$" (.getName %)) (file-seq (File. "."))))

(defn extract-page-tree
  [filename]
  (:content (parse filename)))

(defn vectorize
  [{:keys [tag attrs content] :as el}]
  (letfn [(mk [coll]
            (if (= 1 (count coll))
              (first coll)
              (vec coll)))]
    (if (nil? tag)
      el
      (if (nil? attrs)
        (apply vector tag (map vectorize content))
        (apply vector tag attrs (map vectorize content))))))

(defn convert-to-html [filename]
  (html5 (map vectorize (extract-page-tree filename))))

(defn -main
  "I don't do a whole lot."
  [& args]
  (map #(do (println "Converting " (.getName %) "...")
            (spit (str % ".html") (convert-to-html %)))
       (files-list)))
