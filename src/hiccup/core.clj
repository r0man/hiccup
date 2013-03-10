(ns hiccup.core
  "Library for rendering a tree of vectors into a string of HTML.
  Pre-compiles where possible for performance."
  (:use [hiccup.compiler :only [compile-html *html-mode*]]
        [hiccup.util :only [escape-html]]))

(defmacro html
  "Render Clojure data structures to a string of HTML."
  [options & content]
  (if-let [mode (and (map? options) (:mode options))]
    (binding [*html-mode* mode]
      `(binding [hiccup.compiler/*html-mode* ~mode]
         ~(apply compile-html content)))
    (apply compile-html options content)))

(defmacro node
  "Render ClojureScript data structures to a HTML node."
  [options & content]
  `(hiccup.core/fragment (hiccup.core/html ~options ~@content)))

(def ^{:doc "Alias for hiccup.util/escape-html"}
  h escape-html)
