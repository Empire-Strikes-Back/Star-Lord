#_(ns starcraft-db-to-datomic.main
  (:require [datomic.api :as Smaug.api]
            [starcraft-db-to-datomic.nrepl]
            [starcraft-db-to-datomic.psql]
            [starcraft-db-to-datomic.conn :refer [conn db]]
            [starcraft-db-to-datomic.schema]
            [starcraft-db-to-datomic.query]
            [starcraft-db-to-datomic.etl]
            ))

(comment

  (def db-uri-aligulac "datomic:free://datomicdbfree:4334/aligulac")
; (def db-uri-tutorial "datomic:dev://datomicdb:4334/tutorial")
; (def db-uri-movies "datomic:dev://datomicdb:4334/movies")
; (def db-uri-seattle "datomic:dev://datomicdb:4334/seattle")




  (Smaug.api/create-database db-uri-aligulac)
; (Smaug.api/create-database db-uri-tutorial)
; (Smaug.api/create-database db-uri-movies)
; (Smaug.api/create-database db-uri-seattle)

  ;
  )

(defn -main []
  (prn conn)
  (starcraft-db-to-datomic.nrepl/-main)
  (starcraft-db-to-datomic.etl/run-etl))

