(ns hiccup.test.form
  (:use [hiccup.form :only [hidden-field text-field check-box password-field email-field]]
        [hiccup.form :only [radio-button select-options drop-down text-area file-upload]]
        [hiccup.form :only [label submit-button reset-button form-to]])
  (:use-macros [hiccup.core :only [html]]
               [hiccup.form :only [with-group]]))

(defn test-hidden-field []
  (assert (= (html (hidden-field :foo "bar"))
             "<input id=\"foo\" name=\"foo\" type=\"hidden\" value=\"bar\" />")))

(defn test-hidden-field-with-extra-atts []
  (assert (= (html (hidden-field {:class "classy"} :foo "bar"))
             "<input class=\"classy\" id=\"foo\" name=\"foo\" type=\"hidden\" value=\"bar\" />")))

(defn test-text-field []
  (assert (= (html (text-field :foo))
             "<input id=\"foo\" name=\"foo\" type=\"text\" />")))

(defn test-text-field-with-extra-atts []
  (assert (= (html (text-field {:class "classy"} :foo "bar"))
             "<input class=\"classy\" id=\"foo\" name=\"foo\" type=\"text\" value=\"bar\" />")))

(defn test-check-box []
  (assert (= (html (check-box :foo true))
             (str "<input checked=\"checked\" id=\"foo\" name=\"foo\""
                  " type=\"checkbox\" value=\"true\" />"))))

(defn test-check-box-with-extra-atts []
  (assert (= (html (check-box {:class "classy"} :foo true 1))
             (str "<input checked=\"checked\" class=\"classy\" id=\"foo\" name=\"foo\""
                  " type=\"checkbox\" value=\"1\" />"))))

(defn test-password-field []
  (assert (= (html (password-field :foo "bar"))
             "<input id=\"foo\" name=\"foo\" type=\"password\" value=\"bar\" />")))

(defn test-password-field-with-extra-atts []
  (assert (= (html (password-field {:class "classy"} :foo "bar"))
             "<input class=\"classy\" id=\"foo\" name=\"foo\" type=\"password\" value=\"bar\" />")))

(defn test-email-field []
  (assert (= (html (email-field :foo "bar"))
             "<input id=\"foo\" name=\"foo\" type=\"email\" value=\"bar\" />")))

(defn test-email-field-with-extra-atts []
  (assert (= (html (email-field {:class "classy"} :foo "bar"))
             "<input class=\"classy\" id=\"foo\" name=\"foo\" type=\"email\" value=\"bar\" />")))

(defn test-radio-button []
  (assert (= (html (radio-button :foo true 1))
             (str "<input checked=\"checked\" id=\"foo-1\" name=\"foo\""
                  " type=\"radio\" value=\"1\" />"))))

(defn test-radio-button-with-extra-atts []
  (assert (= (html (radio-button {:class "classy"} :foo true 1))
             (str "<input checked=\"checked\" class=\"classy\" id=\"foo-1\" name=\"foo\""
                  " type=\"radio\" value=\"1\" />"))))

(defn test-select-options []
  (assert (= (html (select-options ["foo" "bar" "baz"]))
             "<option>foo</option><option>bar</option><option>baz</option>"))
  (assert (= (html (select-options ["foo" "bar"] "bar"))
             "<option>foo</option><option selected=\"selected\">bar</option>"))
  (assert (= (html (select-options [["Foo" 1] ["Bar" 2]]))
             "<option value=\"1\">Foo</option><option value=\"2\">Bar</option>")))

(defn test-drop-down []
  (let [options ["op1" "op2"]
        selected "op1"
        select-options (html (select-options options selected))]
    (assert (= (html (drop-down :foo options selected))
               (str "<select id=\"foo\" name=\"foo\">" select-options "</select>")))))

(defn test-drop-down-with-extra-atts []
  (let [options ["op1" "op2"]
        selected "op1"
        select-options (html (select-options options selected))]
    (assert (= (html (drop-down {:class "classy"} :foo options selected))
               (str "<select class=\"classy\" id=\"foo\" name=\"foo\">"
                    select-options "</select>")))))

(defn test-text-area []
  (assert (= (html (text-area :foo "bar"))
             "<textarea id=\"foo\" name=\"foo\">bar</textarea>")))

(defn test-text-area-field-with-extra-atts []
  (assert (= (html (text-area {:class "classy"} :foo "bar"))
             "<textarea class=\"classy\" id=\"foo\" name=\"foo\">bar</textarea>")))

(defn test-text-area-escapes []
  (assert (= (html (text-area :foo "bar</textarea>"))
             "<textarea id=\"foo\" name=\"foo\">bar&lt;/textarea&gt;</textarea>")))

(defn test-file-field []
  (assert (= (html (file-upload :foo))
             "<input id=\"foo\" name=\"foo\" type=\"file\" />")))

(defn test-file-field-with-extra-atts []
  (assert (= (html (file-upload {:class "classy"} :foo))
             (str "<input class=\"classy\" id=\"foo\" name=\"foo\""
                  " type=\"file\" />"))))

(defn test-label []
  (assert (= (html (label :foo "bar"))
             "<label for=\"foo\">bar</label>")))

(defn test-label-with-extra-atts []
  (assert (= (html (label {:class "classy"} :foo "bar"))
             "<label class=\"classy\" for=\"foo\">bar</label>")))

(defn test-submit []
  (assert (= (html (submit-button "bar"))
             "<input type=\"submit\" value=\"bar\" />")))

(defn test-submit-button-with-extra-atts []
  (assert (= (html (submit-button {:class "classy"} "bar"))
             "<input class=\"classy\" type=\"submit\" value=\"bar\" />")))

(defn test-reset-button []
  (assert (= (html (reset-button "bar"))
             "<input type=\"reset\" value=\"bar\" />")))

(defn test-reset-button-with-extra-atts []
  (assert (= (html (reset-button {:class "classy"} "bar"))
             "<input class=\"classy\" type=\"reset\" value=\"bar\" />")))

(defn test-form-to []
  (assert (= (html (form-to [:post "/path"] "foo" "bar"))
             "<form action=\"/path\" method=\"POST\">foobar</form>")))

(defn test-form-to-with-hidden-method []
  (assert (= (html (form-to [:put "/path"] "foo" "bar"))
             (str "<form action=\"/path\" method=\"POST\">"
                  "<input id=\"_method\" name=\"_method\" type=\"hidden\" value=\"PUT\" />"
                  "foobar</form>"))))

(defn test-form-to-with-extr-atts []
  (assert (= (html (form-to {:class "classy"} [:post "/path"] "foo" "bar"))
             "<form action=\"/path\" class=\"classy\" method=\"POST\">foobar</form>")))

(defn test-with-group []
  ;; hidden-field
  (assert (= (html (with-group :foo (hidden-field :bar "val")))
             "<input id=\"foo-bar\" name=\"foo[bar]\" type=\"hidden\" value=\"val\" />"))
  ;; text-field
  (assert (= (html (with-group :foo (text-field :bar)))
             "<input id=\"foo-bar\" name=\"foo[bar]\" type=\"text\" />"))
  ;; checkbox
  (assert (= (html (with-group :foo (check-box :bar)))
             "<input id=\"foo-bar\" name=\"foo[bar]\" type=\"checkbox\" value=\"true\" />"))
  ;; password-field
  (assert (= (html (with-group :foo (password-field :bar)))
             "<input id=\"foo-bar\" name=\"foo[bar]\" type=\"password\" />"))
  ;; radio-button
  (assert (= (html (with-group :foo (radio-button :bar false "val")))
             "<input id=\"foo-bar-val\" name=\"foo[bar]\" type=\"radio\" value=\"val\" />"))
  ;; drop-down
  (assert (= (html (with-group :foo (drop-down :bar [])))
             (str "<select id=\"foo-bar\" name=\"foo[bar]\"></select>")))
  ;; text-area
  (assert (= (html (with-group :foo (text-area :bar)))
             (str "<textarea id=\"foo-bar\" name=\"foo[bar]\"></textarea>")))
  ;; file-upload
  (assert (= (html (with-group :foo (file-upload :bar)))
             "<input id=\"foo-bar\" name=\"foo[bar]\" type=\"file\" />"))
  ;; label
  (assert (= (html (with-group :foo (label :bar "Bar")))
             "<label for=\"foo-bar\">Bar</label>"))
  ;; multiple with-groups
  (assert (= (html (with-group :foo (with-group :bar (text-field :baz))))
             "<input id=\"foo-bar-baz\" name=\"foo[bar][baz]\" type=\"text\" />"))
  ;; multiple elements
  (assert (= (html (with-group :foo (label :bar "Bar") (text-field :var)))
             "<label for=\"foo-bar\">Bar</label><input id=\"foo-var\" name=\"foo[var]\" type=\"text\" />")))

(defn test []
  (test-hidden-field)
  (test-hidden-field-with-extra-atts)
  (test-text-field)
  (test-text-field-with-extra-atts)
  (test-check-box)
  (test-check-box-with-extra-atts)
  (test-password-field)
  (test-password-field-with-extra-atts)
  (test-email-field)
  (test-email-field-with-extra-atts)
  (test-radio-button)
  (test-radio-button-with-extra-atts)
  (test-select-options)
  (test-drop-down)
  (test-drop-down-with-extra-atts)
  (test-text-area)
  (test-text-area-field-with-extra-atts)
  (test-text-area-escapes)
  (test-file-field)
  (test-file-field-with-extra-atts)
  (test-label)
  (test-label-with-extra-atts)
  (test-submit)
  (test-submit-button-with-extra-atts)
  (test-reset-button)
  (test-reset-button-with-extra-atts)
  (test-form-to)
  (test-form-to-with-hidden-method)
  (test-form-to-with-extr-atts)
  (test-with-group))
