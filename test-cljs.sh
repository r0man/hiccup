#!/usr/bin/env bash
if [ ! -f target/hiccup-test.js ]; then
    lein compile
fi
echo "hiccup.test.run()" | d8 --shell target/hiccup-test.js
