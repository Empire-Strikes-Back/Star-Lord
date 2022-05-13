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

   [Ripley.core])
  (:gen-class))

(do (set! *warn-on-reflection* true) (set! *unchecked-math* true))

(defonce stateA (atom nil))

(defn reload
  []
  (require
   '[Star-Lord.seed]
   '[Star-Lord.main]
   :reload))

(defn -main
  [& args]
  #_(println "i dont want my next job")
  (println "oh, i'm sorry - i dont know how this machine works")
  #_(println "Kuiil has spoken")
  (Ripley.core/process {:main-ns 'Star-Lord.main}))