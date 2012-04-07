(ns hiccup.macro
  (:use [hiccup.def :only (wrap-attrs)]))

(defmacro defelem
  "Defines a function that will return a element vector. If the first argument
  passed to the resulting function is a map, it merges it with the attribute
  map of the returned element value."
  [name & fdecl]
  (let [ns-name (-> &env :ns :name)
        fn-name (symbol (str ns-name "/" name))]
    `(do (defn ~name ~@fdecl)
         ;; (alter-meta! (fn-name ~name) update-in [:arglists] #'update-arglists)
         (set! ~fn-name (hiccup.def/wrap-attrs ~fn-name)))))
