#!/bin/bash
echo "============================"
echo "=== Starting up REST API ==="
echo "============================"
cd ../fruits-impl
mvn clean package # generate jar to copy
cd target
mv fruits-impl-1.0.0.jar ../../docker/api/api.jar
cd ../..
docker-compose down -v
docker-compose up &
