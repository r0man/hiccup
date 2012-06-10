#!/usr/bin/env bash
if [ ! -f target/hiccup-test.js ]; then
    lein cljsbuild once
fi
echo "hiccup.test.run()" | d8 --shell target/hiccup-test.js
