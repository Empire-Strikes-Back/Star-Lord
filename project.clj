(defproject starcraft-db-to-datomic "1"
  :main starcraft-db-to-datomic.main
  :plugins [[cider/cider-nrepl "0.18.0"]
            [nightlight/lein-nightlight "RELEASE"]]
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [cider/piggieback "0.3.10"]
                 [figwheel-sidecar "0.5.16"]
                 [nrepl "0.5.3"]
                 [com.datomic/datomic-free "0.9.5656"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [org.postgresql/postgresql "42.2.5.jre7"]]
  :repl-options {:init-ns starcraft-db-to-datomic.main
                 :main starcraft-db-to-datomic.main})
