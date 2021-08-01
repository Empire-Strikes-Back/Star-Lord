#!/bin/bash

repl(){
  clj -M:repl
}


download_aligulac(){
  wget http://static.aligulac.com/aligulac.sql.gz -P ./data 
}

load_aligulac_into_psql(){
    gunzip -c ./data/aligulac.sql.gz | PGPASSWORD=postgres psql -h postgresdb -p 5432 -d aligulac -U aligulac 
    #  psql -h postgresdb -p 5432 -d aligulac -c  "CREATE ROLE aligulac"
}

dowload_datomic(){
  curl -SL https://my.datomic.com/downloads/free/${DATOMIC_VERSION} -o /tmp/datomic.zip \
  && unzip /tmp/datomic.zip -d /opt \
  && mv /opt/datomic-free-${DATOMIC_VERSION} $DATOMIC_HOME \
  && rm -f /tmp/datomic.zip

  cp resources/transactor.properties $DATOMIC_HOME/config/transactor.properties
}

"$@"
