

#_(ns core.schema
    (:require [datomic.api :as Smaug.api]
              [starcraft-db-to-datomic.conn :refer [conn db cdb]]
              [clojure.repl :refer :all]))

(comment

  (def db-uri "datomic:dev://datomicdb:4334/dayofdatomic")

  (def conn (Smaug.api/connect db-uri))

  (defn cdb [] (Smaug.api/db conn))

  (cdb))



;   (def design-data-0 (read-string (slurp "core/day2014/design-data-0.edn")))

; (Smaug.api/transact conn design-data-0)

(comment

;; find the idents of all schema elements in the system
  (sort (Smaug.api/q '[:find [?ident ...]
                       :where [_ :db/ident ?ident]]
                     (cdb)))


;; find just the attributes
  (sort (Smaug.api/q '[:find [?ident ...]
                       :where
                       [?e :db/ident ?ident]
                       [_ :db.install/attribute ?e]]
                     (cdb)))


;; find jsut the data functions 
  (sort (Smaug.api/q '[:find [?ident ...]
                       :where
                       [?e :db/ident ?ident]
                       [_ :db.install/function ?e]]
                     (cdb)))


;; documentation of a schema element
  (-> (cdb) (Smaug.api/entity :db.unique/identity) :db/doc)


;; complete details of a schema element
  (-> (cdb) (Smaug.api/entity :uuid) Smaug.api/touch)

;; find all attributes w/ AVET index
  (sort (Smaug.api/q '[:find [?ident ...]
                       :where
                       [?e :db/ident ?ident]
                       [?e :db/index true]
                       [_ :db.install/attribute ?e]]
                     (cdb)))

;; find attributes in the user namespace 
  (sort (Smaug.api/q '[:find [?ident ...]
                       :where
                       [?e :db/ident ?ident]
                       [_ :db.install/attribute ?e]
                       [(namespace ?ident) ?ns]
                       [(= ?ns "match")]]
                     (cdb)))

;; find all reference attributes
  (sort (Smaug.api/q '[:find [?ident ...]
                       :where
                       [?e :db/ident ?ident]
                       [_ :db.install/attribute ?e]
                       [?e :db/valueType :db.type/ref]]
                     (cdb)))

;; cardinality-many
  (sort (Smaug.api/q '[:find [?ident ...]
                       :where
                       [?e :db/ident ?ident]
                       [_ :db.install/attribute ?e]
                       [?e :db/cardinality :db.cardinality/many]]
                     (cdb))))