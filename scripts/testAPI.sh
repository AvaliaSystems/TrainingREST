#!/bin/bash
echo "========================"
echo "=== Testing REST API ==="
echo "========================"
cd ../fruits-specs
mvn clean package # generate jar to copy
cd ../docker/testApi
docker-compose down -v
docker-compose up

