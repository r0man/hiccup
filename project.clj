(defproject hiccup "1.0.0-RC1"
  :description "A fast library for rendering HTML in Clojure"
  :url "http://github.com/weavejester/hiccup"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/clojurescript "0.0-1345"]]
  :source-paths ["src/clj"]
  :test-paths ["test/clj"]
  :plugins [[codox "0.6.0"]
            [lein-cljsbuild "0.2.1"]]
  :hooks [leiningen.cljsbuild]
  :codox {:exclude [hiccup.compiler]}
  :cljsbuild
  {:builds
   {:development
    {:compiler {:output-to "target/hiccup-debug.js"
                :pretty-print true}
     :source-path "src/cljs"}
    :production
    {:compiler {:output-to "target/hiccup.js"
                :optimizations :advanced
                :pretty-print false}
     :jar true
     :source-path "src/cljs"}
    :test
    {:compiler {:output-to "target/hiccup-test.js"
                :optimizations :advanced
                :pretty-print false}
     :source-path "test/cljs"}}
   :repl-listen-port 9000
   :repl-launch-commands
   {"chromium" ["chromium" "http://localhost:9000/index.html"]
    "firefox" ["firefox" "http://localhost:9000/index.html"]}
   :test-commands {"unit" ["./test-cljs.sh"]}}
  :extra-classpath-dirs ["src/clj"])
