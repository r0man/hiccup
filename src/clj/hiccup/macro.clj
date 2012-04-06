(ns hiccup.macro)

(defmacro with-base-url
  "Sets a base URL that will be prepended onto relative URIs. Note that for this
  to work correctly, it needs to be placed outside the html macro."
  [base-url & body]
  `(binding [hiccup.util/*base-url* ~base-url]
     ~@body))
