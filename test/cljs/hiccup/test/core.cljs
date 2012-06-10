(ns hiccup.test.core
  (:use-macros [hiccup.core :only (html)]))

(defn tag-names []
  ;; basic tags
  (assert (= (html [:div]) "<div></div>"))
  (assert (= (html ["div"]) "<div></div>"))
  (assert (= (html ['div]) "<div></div>"))
  ;; tag syntax sugar
  (assert (= (html [:div#foo]) "<div id=\"foo\"></div>"))
  (assert (= (html [:div.foo]) "<div class=\"foo\"></div>"))
  (assert (= (html [:div.foo (str "bar" "baz")])
             "<div class=\"foo\">barbaz</div>"))
  (assert (= (html [:div.a.b]) "<div class=\"a b\"></div>"))
  (assert (= (html [:div.a.b.c]) "<div class=\"a b c\"></div>"))
  (assert (= (html [:div#foo.bar.baz])
             "<div class=\"bar baz\" id=\"foo\"></div>")))

(defn tag-contents []
  ;; empty tags
  (assert (= (html [:div]) "<div></div>"))
  (assert (= (html [:h1]) "<h1></h1>"))
  (assert (= (html [:script]) "<script></script>"))
  (assert (= (html [:text]) "<text />"))
  (assert (= (html [:a]) "<a></a>"))
  (assert (= (html [:iframe]) "<iframe></iframe>"))
  ;; tags containing text
  (assert (= (html [:text "Lorem Ipsum"]) "<text>Lorem Ipsum</text>"))
  ;; contents are concatenated
  (assert (= (html [:body "foo" "bar"]) "<body>foobar</body>"))
  (assert (= (html [:body [:p] [:br]]) "<body><p /><br /></body>"))
  ;; seqs are expanded
  (assert (= (html [:body (list "foo" "bar")]) "<body>foobar</body>"))
  (assert (= (html (list [:p "a"] [:p "b"])) "<p>a</p><p>b</p>"))
  ;; vecs don't expand - error if vec doesn't have tag name
  (try (html (vector [:p "a"] [:p "b"]))
       (assert false)
       (catch js/Error e nil))
  ;; tags can contain tags
  (assert (= (html [:div [:p]]) "<div><p /></div>"))
  (assert (= (html [:div [:b]]) "<div><b></b></div>"))
  (assert (= (html [:p [:span [:a "foo"]]])
             "<p><span><a>foo</a></span></p>")))

(defn tag-attributes []
  ;; tag with blank attribute map
  (assert (= (html [:xml {}]) "<xml />"))
  ;; tag with populated attribute map
  (assert (= (html [:xml {:a "1", :b "2"}]) "<xml a=\"1\" b=\"2\" />"))
  (assert (= (html [:img {"id" "foo"}]) "<img id=\"foo\" />"))
  (assert (= (html [:img {'id "foo"}]) "<img id=\"foo\" />"))
  (assert (= (html [:xml {:a "1", 'b "2", "c" "3"}])
             "<xml a=\"1\" b=\"2\" c=\"3\" />"))
  ;; attribute values are escaped
  (assert (= (html [:div {:id "\""}]) "<div id=\"&quot;\"></div>"))
  ;; boolean attributes
  (assert (= (html [:input {:type "checkbox" :checked true}])
             "<input checked=\"checked\" type=\"checkbox\" />"))
  (assert (= (html [:input {:type "checkbox" :checked false}])
             "<input type=\"checkbox\" />"))
  ;; nil attributes
  (assert (= (html [:span {:class nil} "foo"])
             "<span>foo</span>")))

(defn compiled-tags []
  ;; tag content can be vars
  (assert (= (let [x "foo"] (html [:span x])) "<span>foo</span>"))
  ;; tag content can be forms
  (assert (= (html [:span (str (+ 1 1))]) "<span>2</span>"))
  (assert (= (html [:span ({:foo "bar"} :foo)]) "<span>bar</span>"))
  ;; attributes can contain vars
  (let [x "foo"]
    (assert (= (html [:xml {:x x}]) "<xml x=\"foo\" />"))
    (assert (= (html [:xml {x "x"}]) "<xml foo=\"x\" />"))
    (assert (= (html [:xml {:x x} "bar"]) "<xml x=\"foo\">bar</xml>")))
  ;; attributes are evaluated
  (assert (= (html [:img {:src (str "/foo" "/bar")}])
             "<img src=\"/foo/bar\" />"))
  (assert (= (html [:div {:id (str "a" "b")} (str "foo")])
             "<div id=\"ab\">foo</div>"))
  ;; ;; type hints
  (let [string "x", number 1]
    (assert (= (html [:span ^String string]) "<span>x</span>"))
    (assert (= (html [:span ^Long number]) "<span>1</span>")))
  ;; optimized forms
  (assert (= (html [:ul (for [n (range 3)]
                          [:li n])])
             "<ul><li>0</li><li>1</li><li>2</li></ul>"))
  (assert (= (html [:div (if true
                           [:span "foo"]
                           [:span "bar"])])
             "<div><span>foo</span></div>"))
  ;; values are evaluated only once
  (let [times-called (atom 0)
        foo #(swap! times-called inc)]
    (html [:div (foo)])
    (assert (= @times-called 1))))

(defn render-modes []
  ;; closed tag
  (assert (= (html [:br]) "<br />"))
  (assert (= (html {:mode :xml} [:br]) "<br />"))
  (assert (= (html {:mode :sgml} [:br]) "<br>"))
  (assert (= (html {:mode :html} [:br]) "<br>"))
  ;; boolean attributes
  (assert (= (html {:mode :xml} [:input {:type "checkbox" :checked true}])
             "<input checked=\"checked\" type=\"checkbox\" />"))
  (assert (= (html {:mode :sgml} [:input {:type "checkbox" :checked true}])
             "<input checked type=\"checkbox\">"))
  ;; laziness and binding scope
  (assert (= (html {:mode :sgml} [:html [:link] (list [:link])])
             "<html><link><link></html>")))

(defn test []
  (tag-names)
  (tag-contents)
  (tag-attributes)
  (render-modes)
  (compiled-tags))
