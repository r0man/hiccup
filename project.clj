(defproject org.clojars.r0man/hiccup "1.0.4-SNAPSHOT"
  :description "A fast library for rendering HTML in Clojure"
  :url "http://github.com/weavejester/hiccup"
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :profiles {:dev {:dependencies [[com.cemerick/clojurescript.test "0.0.1"]]}}
  :plugins [[codox "0.6.3"]
            [lein-cljsbuild "0.3.0"]]
  :hooks [leiningen.cljsbuild]
  :codox {:exclude [hiccup.compiler]
          :src-dir-uri "http://github.com/weavejester/hiccup/blob/1.0.2"
          :src-linenum-anchor-prefix "L"}
  :cljsbuild
  {:builds
   [{:compiler {:output-to "target/hiccup-debug.js"
                :optimizations :whitespace
                :pretty-print true}
     :source-paths ["src"]}
    {:compiler {:output-to "target/hiccup.js"
                :optimizations :advanced
                :pretty-print false}
     :source-paths ["src"]}
    {:compiler {:output-to "target/hiccup-test.js"
                :optimizations :whitespace
                :pretty-print true}
     :source-paths ["test"]}]
   :crossover-jar true
   :crossover-path ".crossover-cljs"
   :crossovers [hiccup.element]
   :repl-listen-port 9000
   :repl-launch-commands
   {"chromium" ["chromium" "http://localhost:9000/index.html"]
    "firefox" ["firefox" "http://localhost:9000/index.html"]}
   :test-commands {"unit-tests" ["runners/phantomjs.js" "target/hiccup-test.js"]}})
