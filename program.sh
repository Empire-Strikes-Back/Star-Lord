#!/bin/bash

repl(){
  clj \
    -J-Dclojure.core.async.pool-size=1 \
    -X:Ripley Ripley.core/process \
    :main-ns Star-Lord.main
}


main(){
  clojure \
    -J-Dclojure.core.async.pool-size=1 \
    -M -m Star-Lord.main
}

tag(){
  COMMIT_HASH=$(git rev-parse --short HEAD)
  COMMIT_COUNT=$(git rev-list --count HEAD)
  git tag "$COMMIT_COUNT-$COMMIT_HASH" $COMMIT_HASH
  git tag -l
}

jar(){

  rm -rf out/*.jar
  COMMIT_HASH=$(git rev-parse --short HEAD)
  COMMIT_COUNT=$(git rev-list --count HEAD)
  clojure \
    -X:Genie Genie.core/process \
    :main-ns Star-Lord.main \
    :filename "\"out/Star-Lord-$COMMIT_COUNT-$COMMIT_HASH.jar\"" \
    :paths '["src"]'
}

release(){
  jar
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
