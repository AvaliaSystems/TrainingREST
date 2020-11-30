#!/bin/bash

echo "========================="
echo "=== Building REST API ==="
echo "========================="
cd ../docker/api
rm *.jar
cd ../../gamification-impl
mvn clean package # generate jar to copy
cd target
mv gamification-impl-1.0.0.jar ../../docker/api/api.jar
cd ../..
docker-compose down -v
echo "============================"
echo "=== Starting up REST API ==="
echo "============================"
# had a bug where image was reused instead of being recreated
# I manually removed the image but I hope --force-recreate will
# prevent this
docker-compose up --build --force-recreate
