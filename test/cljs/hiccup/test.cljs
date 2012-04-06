(ns hiccup.test
  (:require [hiccup.test.core :as core]
            [hiccup.test.util :as util]))

(defn run []
  (core/test)
  (util/test))