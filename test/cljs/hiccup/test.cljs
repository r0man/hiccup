(ns hiccup.test
  (:require [hiccup.test.core :as core]
            [hiccup.test.element :as element]
            [hiccup.test.form :as form]
            [hiccup.test.page :as page]
            [hiccup.test.util :as util]))

(defn ^:export run []
  (core/test)
  (element/test)
  (form/test)
  (page/test)
  (util/test)
  "ok")
