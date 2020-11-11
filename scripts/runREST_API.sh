#!/bin/bash
echo "============================"
echo "=== Starting up REST API ==="
echo "============================"
cd ../fruits-impl
mvn clean package # generate jar to copy
cd ..
docker-compose down -v
docker-compose up
