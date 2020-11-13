#!/bin/sh
cd ../gamification-impl/
mvn install
cp target/gamification-impl-1.0.0.jar ../docker/images/springboots/apps
cd ../docker/topologies/
docker-compose build --no-cache
