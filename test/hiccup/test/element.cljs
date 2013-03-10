(ns hiccup.test.element
  (:import goog.Uri)
  (:require-macros [cemerick.cljs.test :refer [is deftest]])
  (:require [hiccup.element :refer [javascript-tag link-to mail-to unordered-list ordered-list]]
            [hiccup.compiler :refer [render-html render-attr-map]]))

(deftest javascript-tag-test
  (is (= (javascript-tag "alert('hello');")
         [:script {:type "text/javascript"}
          "//<![CDATA[\nalert('hello');\n//]]>"])))

(deftest link-to-test
  (is (= (link-to "/")
         [:a {:href (Uri. "/")} nil]))
  (is (= (link-to "/" "foo")
         [:a {:href (Uri. "/")} (list "foo")]))
  (is (= (link-to "/" "foo" "bar")
         [:a {:href (Uri. "/")} (list "foo" "bar")])))

(deftest mail-to-test
  (is (= (mail-to "foo@example.com")
         [:a {:href "mailto:foo@example.com"} "foo@example.com"]))
  (is (= (mail-to "foo@example.com" "foo")
         [:a {:href "mailto:foo@example.com"} "foo"])))

(deftest unordered-list-test
  (is (= (unordered-list ["foo" "bar" "baz"])
         [:ul (list [:li "foo"]
                    [:li "bar"]
                    [:li "baz"])])))

(deftest ordered-list-test
  (is (= (ordered-list ["foo" "bar" "baz"])
         [:ol (list [:li "foo"]
                    [:li "bar"]
                    [:li "baz"])])))
