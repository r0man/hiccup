(ns hiccup.util
  (:require [clojure.string :as str]
            [goog.string :as string]
            [goog.Uri :as Uri]))

(extend-type goog.Uri
  IEquiv
  (-equiv [uri other]
    (-equiv (str uri) (str other))))

(def ^:dynamic *base-url* nil)

(defprotocol ToString
  (to-str [x] "Convert a value into a string."))

(extend-protocol ToString
  goog.Uri
  (to-str [u]
    (if (or (. u (hasDomain))
            (not (re-matches #"^/.*" (. u (getPath)))))
      (str u)
      (str *base-url* u)))
  nil
  (to-str [_] "")
  number
  (to-str [x] (str x))
  string
  (to-str [x] (name x))
  object
  (to-str [x]
    (str x)))

(defn as-str
  "Converts its arguments into a string using to-str."
  [& xs]
  (apply str (map to-str xs)))

(defprotocol ToURI
  (^goog.Uri to-uri [x] "Convert a value into a URI."))

(extend-protocol ToURI
  goog.Uri
  (to-uri [u] u)
  string
  (to-uri [s] (goog.Uri. s)))

(defn escape-html
  "Change special characters into HTML character entities."
  [text]
  (.. (as-str text)
      (replace "&"  "&amp;")
      (replace "<"  "&lt;")
      (replace ">"  "&gt;")
      (replace "\"" "&quot;")))

(def ^:dynamic *encoding* "UTF-8")

(defprotocol URLEncode
  (url-encode [x] "Turn a value into a URL-encoded string."))

(extend-protocol URLEncode
  nil
  (url-encode [n] "")
  number
  (url-encode [n] (str n))
  string
  (url-encode [s]
    (-> (js/escape (name s))
        (str/replace "%20" "+")))
  cljs.core.ObjMap
  (url-encode [m]
    (str/join "&"
              (for [[k v] m]
                (str (url-encode k) "=" (url-encode v)))))
  object
  (url-encode [x] (url-encode (to-str x))))

(defn ^:export url
  "Creates a URL string from a variable list of arguments and an optional
  parameter map as the last argument. For example:
    (url \"/group/\" 4 \"/products\" {:page 9})
    => \"/group/4/products?page=9\""
  [& args]
  (let [params (last args), args (butlast args)]
    (to-uri
     (str (apply str args)
          (if (map? params)
            (str "?" (url-encode params))
            params)))))
