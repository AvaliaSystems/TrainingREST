#!/bin/sh
cd ../gamification-impl/
mvn install
cp target/gamification-impl-1.0.0.jar ../docker/images/springboot/apps
cd ../docker/topologies/
docker-compose build --no-cache
