(ns hiccup.test.util
  (:require [goog.Uri :as Uri])
  (:use [hiccup.util :only (as-str escape-html url url-encode to-str to-uri *base-url*)])
  (:use-macros [hiccup.util :only (with-base-url)]))

(defn test-escaped-chars []
  (assert (= (escape-html "\"") "&quot;"))
  (assert (= (escape-html "<") "&lt;"))
  (assert (= (escape-html ">") "&gt;"))
  (assert (= (escape-html "&") "&amp;"))
  (assert (= (escape-html "foo") "foo")))

(defn test-as-str []
  (assert (= (as-str "foo") "foo"))
  (assert (= (as-str :foo) "foo"))
  (assert (= (as-str 100) "100"))
  (assert (= (as-str "a" :b 3) "ab3"))
  (assert (= (as-str (goog.Uri. "/foo")) "/foo")))

(defn test-to-uri []
  (assert (= (to-str (to-uri "foo")) "foo"))
  (assert (= (to-str (to-uri "/foo/bar")) "/foo/bar"))
  (assert (= (to-str (to-uri "/foo#bar")) "/foo#bar"))
  (with-base-url "/foo"
    (assert (= (to-str (to-uri "/bar")) "/foo/bar"))
    (assert (= (to-str (to-uri "http://example.com")) "http://example.com"))
    (assert (= (to-str (to-uri "bar")) "bar"))
    (assert (= (to-str (to-uri "../bar")) "../bar"))))

(defn test-url-encode []
  (assert (= "" (url-encode nil)))
  (assert (= "1" (url-encode 1)))
  ;; strings
  (assert (= "a" (url-encode "a")))
  (assert (= "a+b" (url-encode "a b")))
  (assert (= "%26" (url-encode "&")))
  ;; parameter maps
  (assert (= "a=b" (url-encode {"a" "b"})))
  (assert (= "a=b" (url-encode {:a "b"})))
  (assert (= "a=b&c=d" (url-encode {:a "b" :c "d"})))
  (assert (= "a=%26" (url-encode {:a "&"})))
  (assert (= "%E9=%E8" (url-encode {:é "è"})))
  ;; different encodings
  (assert (= "iroha=%u3044%u308D%u306F" (url-encode {:iroha "いろは"}))))

(defn test-url []
  (assert (= (to-str (goog.Uri. "foo")) (to-str (url "foo"))))
  (assert (= (to-str (goog.Uri. "foo/1")) (to-str (url "foo/" 1))))
  (assert (= (to-str (goog.Uri. "/foo/bar")) (to-str (url "/foo/" "bar"))))
  (assert (= (to-str (goog.Uri. "?a=b")) (to-str (url {:a "b"}))))
  (assert (= (to-str (goog.Uri. "foo?a=%26")) (to-str (url "foo" {:a "&"}))))
  (assert (= (to-str (goog.Uri. "/foo/1/bar?page=2")) (to-str (url "/foo/" 1 "/bar" {:page 2})))))

(defn test []
  (test-as-str)
  (test-escaped-chars)
  (test-url-encode)
  (test-to-uri)
  (test-url))
