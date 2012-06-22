(ns hiccup.page
  (:use [hiccup.util :only [to-uri]])
  (:use-macros [hiccup.core :only [html]]))

(def doctype
  {:html4
   (str "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" "
        "\"http://www.w3.org/TR/html4/strict.dtd\">\n")
   :xhtml-strict
   (str "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
        "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n")
   :xhtml-transitional
   (str "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" "
        "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n")
   :html5
   "<!DOCTYPE html>\n"})

(defn ^:export xhtml-tag
  "Create an XHTML element for the specified language."
  [lang & contents]
  [:html {:xmlns "http://www.w3.org/1999/xhtml"
          "xml:lang" lang
          :lang lang}
    contents])

(defn ^:export xml-declaration
  "Create a standard XML declaration for the following encoding."
  [encoding]
  (str "<?xml version=\"1.0\" encoding=\"" encoding "\"?>\n"))

(defn ^:export include-js
  "Include a list of external javascript files."
  [& scripts]
  (for [script scripts]
    [:script {:type "text/javascript", :src (to-uri script)}]))

(defn ^:export include-css
  "Include a list of external stylesheet files."
  [& styles]
  (for [style styles]
    [:link {:type "text/css", :href (to-uri style), :rel "stylesheet"}]))
