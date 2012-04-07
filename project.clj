(defproject hiccup "1.0.0-RC1"
  :description "A fast library for rendering HTML in Clojure"
  :url "http://github.com/weavejester/hiccup"
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :source-paths ["src/clj"]
  :test-paths ["test/clj"]
  :plugins [[codox "0.6.0"]
            [lein-cljsbuild "0.1.6"]]
  :hooks [leiningen.cljsbuild]
  :codox {:exclude [hiccup.compiler]}
  :cljsbuild
  {:builds
   {:development
    {:compiler {:output-to "target/hiccup-debug.js"}
     :source-path "src/cljs"}
    :production
    {:compiler {:output-to "target/hiccup.js"
                :optimizations :advanced
                :pretty-print false}
     :jar true
     :source-path "src/cljs"}
    :test
    {:compiler {:output-to "target/hiccup-test.js"
                :optimizations :whitespace
                :pretty-print true}
     :source-path "test/cljs"}}
   :crossovers []
   :crossover-jar true
   :repl-listen-port 9000
   :repl-launch-commands
   {"chromium" ["chromium" "test-resources/repl.html"]
    "firefox" ["firefox" "test-resources/repl.html"]
    "phantom" ["phantomjs" "test-resources/repl.js" "test-resources/repl.html"]}
   :test-commands {"unit" ["phantomjs" "test-resources/test.js" "test-resources/test.html"]}}
  :extra-classpath-dirs ["src/clj"])
