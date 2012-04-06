(ns hiccup.test.util
  (:require [goog.Uri :as Uri])
  (:use [hiccup.util :only (as-str escape-html url-encode to-str to-uri *base-url*)])
  (:use-macros [hiccup.macro :only (with-base-url)])
  ;; (:use clojure.test
  ;;       hiccup.util)
  ;; (:import java.net.URI)
  )

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

;; (defn test-url []
;;   (testing "URL parts and parameters"
;;     ;; (are [u s] (= u s)
;;     ;;   (url "foo")          (URI. "foo")
;;     ;;   (url "foo/" 1)       (URI. "foo/1")
;;     ;;   (url "/foo/" "bar")  (URI. "/foo/bar")
;;     ;;   (url {:a "b"})       (URI. "?a=b")
;;     ;;   (url "foo" {:a "&"}) (URI. "foo?a=%26")
;;     ;;   (url "/foo/" 1 "/bar" {:page 2}) (URI. "/foo/1/bar?page=2"))
;;     ))

(defn test []
  (test-as-str)
  (test-escaped-chars)
  (test-url-encode)
  (test-to-uri)
  ;; (test-url)
  )