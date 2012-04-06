(ns hiccup.test.util
  (:use [hiccup.util :only (as-str escape-html)])
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
  ;; (assert (= (as-str (URI. "/foo")) "/foo"))
  )

;; (deftest test-to-uri
;;   (testing "with no base URL"
;;     (is (= (to-str (to-uri "foo")) "foo"))
;;     (is (= (to-str (to-uri "/foo/bar")) "/foo/bar"))
;;     (is (= (to-str (to-uri "/foo#bar")) "/foo#bar")))
;;   (testing "with base URL"
;;     (with-base-url "/foo"
;;       (is (= (to-str (to-uri "/bar")) "/foo/bar"))
;;       (is (= (to-str (to-uri "http://example.com")) "http://example.com"))
;;       (is (= (to-str (to-uri "bar")) "bar"))
;;       (is (= (to-str (to-uri "../bar")) "../bar")))))

;; (deftest test-url-encode
;;   (testing "strings"
;;     (are [s e] (= (url-encode s) e)
;;       "a"   "a"
;;       "a b" "a+b"
;;       "&"   "%26"))
;;   (testing "parameter maps"
;;     (are [m e] (= (url-encode m) e)
;;       {"a" "b"}       "a=b"
;;       {:a "b"}        "a=b"
;;       {:a "b" :c "d"} "a=b&c=d"
;;       {:a "&"}        "a=%26"
;;       {:é "è"}        "%C3%A9=%C3%A8"))
;;   (testing "different encodings"
;;     (are [e s] (= (with-encoding e (url-encode {:iroha "いろは"})) s)
;;       "UTF-8"       "iroha=%E3%81%84%E3%82%8D%E3%81%AF"
;;       "Shift_JIS"   "iroha=%82%A2%82%EB%82%CD"
;;       "EUC-JP"      "iroha=%A4%A4%A4%ED%A4%CF"
;;       "ISO-2022-JP" "iroha=%1B%24%42%24%24%24%6D%24%4F%1B%28%42")))

;; (deftest test-url
;;   (testing "URL parts and parameters"
;;     (are [u s] (= u s)
;;       (url "foo")          (URI. "foo")
;;       (url "foo/" 1)       (URI. "foo/1")
;;       (url "/foo/" "bar")  (URI. "/foo/bar")
;;       (url {:a "b"})       (URI. "?a=b")
;;       (url "foo" {:a "&"}) (URI. "foo?a=%26")
;;       (url "/foo/" 1 "/bar" {:page 2}) (URI. "/foo/1/bar?page=2"))))

(defn test []
  (test-as-str)
  (test-escaped-chars))