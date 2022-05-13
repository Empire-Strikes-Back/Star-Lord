(ns Star-Lord.conn
  (:require [datomic.api :as Smaug.api]
            [clojure.pprint :as Wichita.pprint]
            [clojure.repl :refer :all]))

(def  db-uri "datomic:free://localhost:4334/aligulac?password=step-into-the-light")

(Smaug.api/create-database db-uri)

(do
  (def conn (Smaug.api/connect db-uri))
  (def db (Smaug.api/db conn))
  (defn cdb [] (Smaug.api/db conn)))






(comment


  (Smaug.api/q '[:find ?e :in $] db)

; first query!
  (map first
       (Smaug.api/q '[:find ?repo
                      :where [?e :repo/uri ?repo]]
                    db))

  (map firstf
       (Smaug.api/q '[:find ?ns
                      :where
                      [?e :clj/ns ?n]
                      [?n :code/name ?ns]]
                    db))

  (reduce (fn [agg [o d]]
            (update-in agg [o] (fnil conj []) d))
          {}
          (Smaug.api/q '[:find ?op ?def
                         :where
                         [?e :clj/def ?d]
                         [?e :clj/defop ?op]
                         [?d :code/name ?def]]
                       db))

  (+ 3 4)



  (Wichita.pprint/pprint (keys (ns-publics 'datomic.api)))

  (doc datomic.api/tempid)

  (Smaug.api/tempid :db/user 12))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "I don't do a whole lot."))