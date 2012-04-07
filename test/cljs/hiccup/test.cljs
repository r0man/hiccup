(ns hiccup.test
  (:require [hiccup.test.core :as core]
            [hiccup.test.element :as element]
            [hiccup.test.form :as form]
            [hiccup.test.util :as util]))

(defn run []
  (core/test)
  (element/test)
  (form/test)
  (util/test))
