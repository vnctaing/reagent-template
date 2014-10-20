(defproject {{full-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj" "src/cljs"{{{cljx-source-paths}}}]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371" :scope "provided"]
                 [ring "1.3.1"]
                 [compojure "1.2.0"]
                 [selmer "0.7.2"]
                 [reagent "0.4.2"]
                 [figwheel "0.1.4-SNAPSHOT"]
                 [environ "1.0.0"]
                 [com.cemerick/piggieback "0.1.3"]
                 [weasel "0.4.0-SNAPSHOT"]
                 [ring/ring-defaults "0.1.2"]
                 [leiningen "2.5.0"]]

  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-environ "1.0.0"]
            [lein-ring "0.8.13"]
            [lein-asset-minifier "0.2.0"]]

  :ring {:handler {{project-ns}}.handler/app}

  :min-lein-version "2.5.0"

  :uberjar-name "{{name}}.jar"

  :minify-assets
  {:assets
    {"resources/public/css/site.min.css" "resources/css/site.css"}}

  :cljsbuild {:builds
              {:dev
                {:source-paths ["src/cljs"{{{cljx-cljsbuild-spath}}}]
                 :compiler
                  {:output-to     "resources/public/js/app.js"
                   :output-dir    "resources/public/js/out"
                   :source-map    "resources/public/js/out.js.map"
                   :externs       ["react/externs/react.js"]
                   :optimizations :none
                   :pretty-print  true}}
               :release
                {:source-paths ["src-cljs"]
                 :compiler
                  {:output-to        "resources/public/js/app.js"
                   :optimizations    :advanced
                   :pretty-print     false
                   :output-wrapper   false
                   :closure-warnings {:non-standard-jsdoc :off}}}}}


  :profiles {:dev {:repl-options {:init-ns {{project-ns}}.handler
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl{{{nrepl-middleware}}}]}

                   :plugins [[lein-figwheel "0.1.4-SNAPSHOT"]{{{project-dev-plugins}}}]

                   :figwheel {:http-server-root "public"
                              :port 3449
                              :css-dirs ["resources/public/css"]}

                   :env {:dev? true}

                   {{#cljx-hook?}}
                   :hooks [cljx.hooks]
                   {{/cljx-hook?}}
                   {{#cljx-build?}}
                   :cljx {:builds [{:source-paths ["src/cljx"]
                                    :output-path "target/generated/clj"
                                    :rules :clj}
                                   {:source-paths ["src/cljx"]
                                    :output-path "target/generated/cljs"
                                    :rules :cljs}]}
                   {{/cljx-build?}}
                   :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]}}}}

             :uberjar {:hooks [{{cljx-uberjar-hook}}leiningen.cljsbuild minify-assets.plugin/hooks]
                       :env {:production true}
                       :omit-source true
                       :aot :all
                       :cljsbuild {:builds {:app
                                            {:source-paths ["env/prod/cljs"]
                                             :compiler
                                             {:optimizations :advanced
                                              :pretty-print false}}}}}})
