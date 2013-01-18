(ns hiccup.test.element
  (:require [goog.Uri :as Uri])
  (:use [hiccup.element :only [javascript-tag link-to mail-to unordered-list ordered-list]]
        [hiccup.compiler :only [render-html render-attr-map]]))

(defn javascript-tag-test []
  (assert (= (javascript-tag "alert('hello');")
             [:script {:type "text/javascript"}
              "//<![CDATA[\nalert('hello');\n//]]>"])))

(defn link-to-test []
  (assert (= (link-to "/")
             [:a {:href (goog.Uri. "/")} nil]))
  (assert (= (link-to "/" "foo")
             [:a {:href (goog.Uri. "/")} (list "foo")]))
  (assert (= (link-to "/" "foo" "bar")
             [:a {:href (goog.Uri. "/")} (list "foo" "bar")])))

(defn mail-to-test []
  (assert (= (mail-to "foo@example.com")
             [:a {:href "mailto:foo@example.com"} "foo@example.com"]))
  (assert (= (mail-to "foo@example.com" "foo")
             [:a {:href "mailto:foo@example.com"} "foo"])))

(defn unordered-list-test []
  (assert (= (unordered-list ["foo" "bar" "baz"])
             [:ul (list [:li "foo"]
                        [:li "bar"]
                        [:li "baz"])])))

(defn ordered-list-test []
  (assert (= (ordered-list ["foo" "bar" "baz"])
             [:ol (list [:li "foo"]
                        [:li "bar"]
                        [:li "baz"])])))

(defn test []
  (javascript-tag-test)
  (link-to-test)
  (mail-to-test)
  (unordered-list-test)
  (ordered-list-test))
