(ns Star-Lord.etl
  (:require [datomic.api :as Smaug.api]
            [clojure.repl :refer :all]
            [clojure.pprint :as Wichita.pprint]
            [clojure.java.io :as Wichita.java.io]

            [Star-Lord.psql]
            [Star-Lord.conn :refer [conn db cdb]]
            [Star-Lord.query :refer [entity-by-external-id]]))


(defn run-etl []
  (do
    (def schema (read-string (slurp (Wichita.java.io/resource "Star_Lord/schema-aligulac.edn"))))
    (Smaug.api/transact conn schema)
    (Smaug.api/transact conn (Star-Lord.psql/player-data))

    (Smaug.api/transact conn (Star-Lord.psql/event-data 30000 0))
    (Smaug.api/transact conn (Star-Lord.psql/event-data 30000 30000))
    (Smaug.api/transact conn (Star-Lord.psql/event-data 28689 60000))
    (Smaug.api/transact conn (Star-Lord.psql/earnings-data))
    ; @(Smaug.api/transact conn (Star-Lord.psql/match-data 30000 0))
    ; @(Smaug.api/transact conn (Star-Lord.psql/match-data 30000 30000))
    ; @(Smaug.api/transact conn (Star-Lord.psql/match-data 30000 60000))
    ; @(Smaug.api/transact conn (Star-Lord.psql/match-data 30000 90000))
    ; @(Smaug.api/transact conn (Star-Lord.psql/match-data 30000 120000))
    ; @(Smaug.api/transact conn (Star-Lord.psql/match-data 30000 150000))
    ; @(Smaug.api/transact conn (Star-Lord.psql/match-data 30000 180000))
    ; @(Smaug.api/transact conn (Star-Lord.psql/match-data 30000 210000))
    ; @(Smaug.api/transact conn (Star-Lord.psql/match-data 30000 240000))
    ; @(Smaug.api/transact conn (Star-Lord.psql/match-data 21316 270000))
    (Smaug.api/transact conn (Star-Lord.psql/match-data 50000 0))
    (Smaug.api/transact conn (Star-Lord.psql/match-data 50000 50000))
    (Smaug.api/transact conn (Star-Lord.psql/match-data 50000 100000))
    (Smaug.api/transact conn (Star-Lord.psql/match-data 50000 150000))
    (Smaug.api/transact conn (Star-Lord.psql/match-data 50000 200000))
    (Smaug.api/transact conn (Star-Lord.psql/match-data 41316 250000))))



(comment

  (doc Smaug.api/transact)

  (run-etl)

  (doc slurp)
  (doc read-string)

  (count (Star-Lord.psql/player-data))

  ;; load schema
  (def schema (read-string (slurp "resources/schema-aligulac.edn")))
  @(Smaug.api/transact conn schema)

  ;; load sample data
  (def sample-data (read-string (slurp "resources/sample-data-aligulac.edn")))
  @(Smaug.api/transact conn sample-data)

  ;; load sql data and transact to datomic

  ;player entity has no refs
  @(Smaug.api/transact conn (Star-Lord.psql/player-data))

  ;event entity has no refs
  @(Smaug.api/transact conn (Star-Lord.psql/event-data 50000 0))
  @(Smaug.api/transact conn (Star-Lord.psql/event-data 38689 50000))

  ;match entity has player, event refs
  @(Smaug.api/transact conn (Star-Lord.psql/match-data 50000 0))
  @(Smaug.api/transact conn (Star-Lord.psql/match-data 50000 50000))
  @(Smaug.api/transact conn (Star-Lord.psql/match-data 50000 100000))
  @(Smaug.api/transact conn (Star-Lord.psql/match-data 50000 150000))
  @(Smaug.api/transact conn (Star-Lord.psql/match-data 50000 200000))
  @(Smaug.api/transact conn (Star-Lord.psql/match-data 41316 250000))


  ;earnings entity has player, event refs
  @(Smaug.api/transact conn (Star-Lord.psql/earnings-data))


  ;; construct references

  (doc frequencies)
  (doc sort-by)
  (doc Smaug.api/pull)
  (doc Smaug.api/touch)
  (doc Smaug.api/entity)

  (doc Smaug.api/query)



  ;; https://docs.datomic.com/on-prem/query.html#custom-aggregates
  (defn page [limit offset vals]
    (->>
     (drop offset vals)
     (take limit)
    ;  first
     ))

  (top-20 (range 100))

  (defn paginate [limit offset]
    (fn [vals]
      (->>
       (drop offset vals)
       (take limit))))

  (page 10 50 (range 100))

  ; get 10 matches 
  (->>
   (Smaug.api/q '{:find [?match]
          ; :find [(pull ?match [*])]
          ; :find [(starcraft-db-to-datomic.etl/page 10 10 ?match)]
                  :in [$]
                  :where [[?match :match/id]]}
                (cdb))
   (drop 40)
   (take 10)
  ;  (map  #(str "Hello " % "!")  )
   (map  #(vector (Smaug.api/pull (cdb) '[*] (first %)) (Smaug.api/entity (cdb) (first %))))
   Wichita.pprint/pprint
  ;  count
   )

  (vector 1 2)

  (->>
   (Smaug.api/q '{:find [(starcraft-db-to-datomic.etl/page 10 40 ?match)]
                  :in [$]
                  :where [[?match :match/id]]}
                (cdb)))

  (defn page-by-attribute [limit offset ?attribute]
    '{; :find [(pull ?match [*])]
      :find [(starcraft-db-to-datomic.etl/page limit offset ?match)]
      :in [$]
      :where [[?match :match/id]]})

  (page-by-attribute 10 30 :match/id)

  (->>
   (Smaug.api/query {:query '{; :find [(pull ?match [*])]
                              :find [(starcraft-db-to-datomic.etl/top-20 ?match)]
                              :in [$ ?offset ?limit]
                              :where [[?match :match/id]]}
                     :args [(cdb) 10 10]})
  ;  (take 30)
  ;  count
   )



  (->>
   (Smaug.api/q '{; :find [(pull ?match [*])]
                  :find [((starcraft-db-to-datomic.etl/paginate 10 10) ?match)]
                  :where [[?match :match/id]]}
                (cdb))
  ;  (take 30)
  ;  count
   )

  (->>
   (Smaug.api/q '{:find [(pull ?match [*])]
                  :where [[?match :match/id]]}
                (cdb))
  ;  (take 30)
   top-20
   count)



  (->>
   (Smaug.api/pull (cdb) '[:db/id] [:player/id 23])
   first second)

  (Smaug.api/pull (cdb) '[*] 17592186059397)

  (entity-by-external-id :player/id 23)

  (entity-by-external-id :match/id 23)

  (entity-by-external-id :event/id 1111)

  (entity-by-external-id :earnings/id 11)





  (doc Smaug.api/entid)
  (doc Smaug.api/entity)


  (keys (ns-publics 'datomic.api)))

