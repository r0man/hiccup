(ns hiccup.test.def
  (:use [hiccup.compiler :only [render-html render-attr-map]])
  (:use-macros [hiccup.def :only [defhtml]]
               [hiccup.macro :only [defelem]]))

(defn test-defhtml []
  ;; basic html function
  (defhtml basic-fn [x] [:span x])
  (assert (= (basic-fn "foo") "<span>foo</span>"))
  ;; html function with overloads
  (defhtml overloaded-fn
    ([x] [:span x])
    ([x y] [:span x [:div y]]))
  (assert (= (overloaded-fn "foo") "<span>foo</span>"))
  (assert (= (overloaded-fn "foo" "bar")
             "<span>foo<div>bar</div></span>")))

(defn test-defelem []
  ;; one overload function
  (defelem one-form-two-args [a b] [b a 3])
  ;; (assert (thrown? IllegalArgumentException (one-form-two-args)))
  ;; (assert (thrown? IllegalArgumentException (one-form-two-args {})))
  ;; (assert (thrown? IllegalArgumentException (one-form-two-args 1)))
  (assert (= [1 0 3] (one-form-two-args 0 1)))
  (assert (= [1 {:foo :bar} 0 3] (one-form-two-args {:foo :bar} 0 1)))
  ;; (assert (thrown? IllegalArgumentException (one-form-two-args 1 2 3)))
  ;; (assert (thrown? IllegalArgumentException (one-form-two-args 1 2 3 4)))
  ;; three overloads function
  (defelem three-forms
    ([] [0])
    ([a] [(* a a) 2])
    ([a b] [b a]))
  (assert (= [0] (three-forms)))
  (assert (= [0 {:foo :bar}] (three-forms {:foo :bar})))
  (assert (= [4 2] (three-forms 2)))
  (assert (= [4 {:foo :bar} 2] (three-forms {:foo :bar} 2)))
  (assert (= [1 0] (three-forms 0 1)))
  (assert (= [1 {:foo :bar} 0] (three-forms {:foo :bar} 0 1)))
  ;; (assert (thrown? IllegalArgumentException (three-forms 1 2 3)))
  ;; (assert (thrown? IllegalArgumentException (three-forms 1 2 3 4)))
  ;; recursive function
  (defelem recursive [a]
    (if (< a 1) [a (inc a)] (recursive (- a 1))))
  (assert (= [0 1] (recursive 4)))
  (assert (= [0 {:foo :bar} 1] (recursive {:foo :bar} 4)))
  ;; merge map if result has one
  (defelem with-map
    ([] [1 {:foo :bar} 2])
    ([a b] [a {:foo :bar} b]))
  (assert (= [1 {:foo :bar} 2] (with-map)))
  (assert (= [1 {:a :b :foo :bar} 2] (with-map {:a :b})))
  (assert (= [1 {:foo :bar} 2] (with-map 1 2)))
  (assert (= [1 {:foo :bar :a :b} 2] (with-map {:a :b} 1 2)))
  ;; preserve meta info
  (defelem three-forms-extra
    "my documentation"
    {:my :attr}
    ([] {:pre [false]} [0])
    ([a] {:pre [(> a 1)]} [1])
    ([a b] {:pre [(> a 1)]} [1 2]))
  ;; (assert (thrown? AssertionError (three-forms-extra)))
  ;; (assert (thrown? AssertionError (three-forms-extra 0)))
  ;; (assert (thrown? AssertionError (three-forms-extra 0 0)))
  ;; (assert (= "my documentation" (:doc (meta #'three-forms-extra))))
  ;; (assert (= :attr (:my (meta #'three-forms-extra))))
  )

(defn test []
  (test-defhtml)
  (test-defelem))