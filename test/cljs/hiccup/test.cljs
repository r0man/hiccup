(ns hiccup.test
  (:require [hiccup.test.core :as core]
            [hiccup.test.util :as util])
  (:use-macros [hiccup.core :only (html)]))

(defn run []
  (core/test)
  (util/test))