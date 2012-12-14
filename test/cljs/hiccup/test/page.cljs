(ns hiccup.test.page
  (:require [goog.Uri :as Uri])
  (:use [hiccup.page :only [include-js include-css]]
        [hiccup.compiler :only [*html-mode*]])
  (:use-macros [hiccup.page :only [html4 html5 xhtml]]))

(defn html4-test []
  (assert (= (html4 [:body [:p "Hello" [:br] "World"]])
             (str "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" "
                  "\"http://www.w3.org/TR/html4/strict.dtd\">\n"
                  "<html><body><p>Hello<br>World</p></body></html>"))))

(defn xhtml-test []
  (assert (= (xhtml [:body [:p "Hello" [:br] "World"]])
             (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                  "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                  "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                  "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                  "<body><p>Hello<br />World</p></body></html>")))
  (assert (= (xhtml {:lang "en"} [:body "Hello World"])
             (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                  "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                  "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                  "<html lang=\"en\" xml:lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">"
                  "<body>Hello World</body></html>")))
  (assert (= (xhtml {:encoding "ISO-8859-1"} [:body "Hello World"])
             (str "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n"
                  "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                  "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                  "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                  "<body>Hello World</body></html>"))))

(defn html5-test []
  ;; HTML mode
  (assert (= (html5 [:body [:p "Hello" [:br] "World"]])
             "<!DOCTYPE html>\n<html><body><p>Hello<br>World</p></body></html>"))
  (assert (= (html5 {:lang "en"} [:body "Hello World"])
             "<!DOCTYPE html>\n<html lang=\"en\"><body>Hello World</body></html>"))
  (assert (= (html5 {:prefix "og: http://ogp.me/ns#"}
                    [:body "Hello World"])
             (str "<!DOCTYPE html>\n"
                  "<html prefix=\"og: http://ogp.me/ns#\">"
                  "<body>Hello World</body></html>")))
  (assert (= (html5 {:prefix "og: http://ogp.me/ns#"
                     :lang "en"}
                    [:body "Hello World"])
             (str "<!DOCTYPE html>\n"
                  "<html lang=\"en\" prefix=\"og: http://ogp.me/ns#\">"
                  "<body>Hello World</body></html>")))
  ;; XML mode
  (assert (= (html5 {:xml? true} [:body [:p "Hello" [:br] "World"]])
             (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                  "<!DOCTYPE html>\n<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                  "<body><p>Hello<br />World</p></body></html>")))
  (assert (= (html5 {:xml? true, :lang "en"} [:body "Hello World"])
             (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                  "<!DOCTYPE html>\n"
                  "<html lang=\"en\" xml:lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">"
                  "<body>Hello World</body></html>")))
  (assert (= (html5 {:xml? true,
                     "xml:og" "http://ogp.me/ns#"} [:body "Hello World"])
             (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                  "<!DOCTYPE html>\n"
                  "<html xml:og=\"http://ogp.me/ns#\" xmlns=\"http://www.w3.org/1999/xhtml\">"
                  "<body>Hello World</body></html>")))
  (assert (= (html5 {:xml? true, :lang "en"
                     "xml:og" "http://ogp.me/ns#"} [:body "Hello World"])
             (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                  "<!DOCTYPE html>\n"
                  "<html lang=\"en\" xml:lang=\"en\" xml:og=\"http://ogp.me/ns#\" xmlns=\"http://www.w3.org/1999/xhtml\">"
                  "<body>Hello World</body></html>"))))

(defn include-js-test []
  (assert (= (include-js "foo.js")
             (list [:script {:type "text/javascript", :src (goog.Uri. "foo.js")}])))
  (assert (= (include-js "foo.js" "bar.js")
             (list [:script {:type "text/javascript", :src (goog.Uri. "foo.js")}]
                   [:script {:type "text/javascript", :src (goog.Uri. "bar.js")}]))))

(defn include-css-test []
  (assert (= (include-css "foo.css")
             (list [:link {:type "text/css", :href (goog.Uri. "foo.css"), :rel "stylesheet"}])))
  (assert (= (include-css "foo.css" "bar.css")
             (list [:link {:type "text/css", :href (goog.Uri. "foo.css"), :rel "stylesheet"}]
                   [:link {:type "text/css", :href (goog.Uri. "bar.css"), :rel "stylesheet"}]))))

(defn test []
  (html4-test)
  (xhtml-test)
  (html5-test)
  (include-js-test)
  (include-css-test))
