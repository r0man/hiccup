(ns hiccup.compiler
  (:use [hiccup.util :only (as-str escape-html)]))

(def ^:dynamic *html-mode* :xml)

(defn- xml-mode? []
  (= *html-mode* :xml))

(defn- end-tag []
  (if (xml-mode?) " />" ">"))

(defn- xml-attribute [name value]
  (str " " (as-str name) "=\"" (escape-html value) "\""))

(defn- render-attribute [[name value]]
  (cond
   (true? value)
   (if (xml-mode?)
     (xml-attribute name name)
     (str " " (as-str name)))
   (not value)
   ""
   :else
   (xml-attribute name value)))

(defn render-attr-map [attrs]
  (apply str
         (sort (map render-attribute attrs))))

(def ^{:doc "Regular expression that parses a CSS-style id and class from an element name."
       :private true}
  re-tag #"([^\s\.#]+)(?:#([^\s\.#]+))?(?:\.([^\s#]+))?")

(def ^{:doc "A list of elements that need an explicit ending tag when rendered."
       :private true}
  container-tags
  #{"a" "b" "body" "canvas" "dd" "div" "dl" "dt" "em" "fieldset" "form" "h1" "h2" "h3"
    "h4" "h5" "h6" "head" "html" "i" "iframe" "label" "li" "ol" "option" "pre"
    "script" "span" "strong" "style" "table" "textarea" "ul"})

(defn normalize-element
  "Ensure an element vector is of the form [tag-name attrs content]."
  [[tag & content]]
  (when (not (or (keyword? tag) (symbol? tag) (string? tag)))
    (throw (js/Error. (str tag " is not a valid element name."))))
  (let [[_ tag id class] (re-matches re-tag (as-str tag))
        tag-attrs        {:id id
                          :class (if class (.replace ^String class "." " "))}
        map-attrs        (first content)]
    (if (map? map-attrs)
      [tag (merge tag-attrs map-attrs) (next content)]
      [tag tag-attrs content])))

(defn- render-element
  "Render an element vector as a HTML element."
  [element]
  (let [[tag attrs content] (normalize-element element)]
    (if (or content (container-tags tag))
      (str "<" tag (render-attr-map attrs) ">"
           (render-html content)
           "</" tag ">")
      (str "<" tag (render-attr-map attrs) (end-tag)))))

(defmulti render-html
  "Turn a Clojure data type into a string of HTML."
  type)

(defmethod render-html Cons [cons]
  (apply str (map render-html cons)))

(defmethod render-html LazySeq [lazy-seq]
  (apply str (map render-html lazy-seq)))

(defmethod render-html List [list]
  (apply str (map render-html list)))

(defmethod render-html IndexedSeq [indexed-seq]
  (apply str (map render-html indexed-seq)))

(defmethod render-html PersistentVector [vector]
  (render-element vector))

(defmethod render-html Vector [vector]
  (render-element vector))

(defmethod render-html :default [x]
  (as-str x))
