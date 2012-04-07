(ns hiccup.macro)

(defmacro defelem
  "Defines a function that will return a element vector. If the first argument
  passed to the resulting function is a map, it merges it with the attribute
  map of the returned element value."
  [name & fdecl]
  (let [ns-name (-> &env :ns :name)
        var (symbol (str ns-name "/" name))]
    `(do (defn ~name ~@fdecl)
         ;; (alter-meta! (var ~name) update-in [:arglists] #'update-arglists)
         (set! ~var (hiccup.def/wrap-attrs ~var)))))
