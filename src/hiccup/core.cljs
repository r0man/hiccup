(ns hiccup.core
  (:use [hiccup.util :only [escape-html]])
  (:require [goog.dom :as dom]))

(def ^{:doc "Alias for hiccup.util/escape-html"}
  h escape-html)

(defn fragment
  "Returns the HTML string `s` as a document fragment."
  [s] (dom/htmlToDocumentFragment s))
