(ns hiccup.core
  (:use [hiccup.util :only [escape-html]])
  (:require [goog.dom :as dom]))

(def ^{:doc "Alias for hiccup.util/escape-html"}
  h escape-html)

(defn render
  "Render the HTML string as a document fragment."
  [html] (dom/htmlToDocumentFragment html))
