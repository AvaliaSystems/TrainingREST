#!/bin/bash

echo "========================="
echo "=== Building REST API ==="
echo "========================="
cd ../gamification-impl
mvn clean package # generate jar to copy
cd target
mv gamification-impl-1.0.0.jar ../../docker/api/api.jar
cd ../..
docker-compose down -v
echo "============================"
echo "=== Starting up REST API ==="
echo "============================"
docker-compose up &
