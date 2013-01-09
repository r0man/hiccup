(defproject org.clojars.r0man/hiccup "1.0.2"
  :description "A fast library for rendering HTML in Clojure"
  :url "http://github.com/weavejester/hiccup"
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :source-paths ["src/clj"]
  :test-paths ["test/clj"]
  :plugins [[codox "0.6.3"]
            [lein-cljsbuild "0.2.10"]]
  :codox {:exclude [hiccup.compiler]
          :src-dir-uri "http://github.com/weavejester/hiccup/blob/1.0.2"
          :src-linenum-anchor-prefix "L"}
  :hooks [leiningen.cljsbuild]
  :cljsbuild
  {:builds
   [{:compiler {:output-to "target/hiccup-debug.js"
                :optimizations :whitespace
                :pretty-print true}
     :source-path "src/cljs"}
    {:compiler {:output-to "target/hiccup.js"
                :optimizations :advanced
                :pretty-print false
                :jar true}
     :source-path "src/cljs"}
    {:compiler {:output-to "target/hiccup-test.js"
                :optimizations :advanced
                :pretty-print true}
     :source-path "test/cljs"}]
   :repl-listen-port 9000
   :repl-launch-commands
   {"chromium" ["chromium" "http://localhost:9000/index.html"]
    "firefox" ["firefox" "http://localhost:9000/index.html"]}
   :test-commands {"unit" ["./test-cljs.sh"]}}
  :extra-classpath-dirs ["src/clj"])
