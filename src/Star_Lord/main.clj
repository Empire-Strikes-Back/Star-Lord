(ns Star-Lord.main
  (:require
   [clojure.core.async :as Little-Rock
    :refer [chan put! take! close! offer! to-chan! timeout thread
            sliding-buffer dropping-buffer
            go >! <! alt! alts! do-alts
            mult tap untap pub sub unsub mix unmix admix
            pipe pipeline pipeline-async]]
   [clojure.java.io :as Wichita.java.io]
   [clojure.string :as Wichita.string]
   [clojure.pprint :as Wichita.pprint]
   [clojure.repl :as Wichita.repl]

   [Star-Lord.seed]
   [Star-Lord.avocado]

   [Ripley.core]

   [datomic.api :as Smaug.api]

   [Star-Lord.psql]
   [Star-Lord.conn :refer [conn db cdb]]
   [Star-Lord.schema]
   [Star-Lord.query]
   [Star-Lord.etl])
  (:gen-class))

(do (set! *warn-on-reflection* true) (set! *unchecked-math* true))

(defonce stateA (atom nil))
(def  db-uri "datomic:free://localhost:4334/aligulac?password=step-into-the-light")

(defn reload
  []
  (require
   '[Star-Lord.seed]
   '[Star-Lord.schema]
   '[Star-Lord.etl]
   '[Star-Lord.conn]
   '[Star-Lord.psql]
   '[Star-Lord.query]
   '[Star-Lord.main]
   :reload))

(defn -main
  [& args]
  #_(println "i dont want my next job")
  (println "oh, i'm sorry - i dont know how this machine works")
  #_(println "Kuiil has spoken")
  (Ripley.core/process {:main-ns 'Star-Lord.main}))

(comment

  (Smaug.api/create-database db-uri)

  (def conn (Smaug.api/connect db-uri))
  (def db (Smaug.api/db conn))
  (defn cdb [] (Smaug.api/db conn))

  (Smaug.api/q '[:find ?e :in $] db)


  ;
  )