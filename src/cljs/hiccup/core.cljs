(ns hiccup.core
  (:use [hiccup.util :only (escape-html)]))

(def ^{:export true :doc "Alias for hiccup.util/escape-html"}
  h escape-html)
