#!/bin/sh

cd ../gamification-impl/
mvn install
rm -f ../docker/images/springboot/apps/gamification-impl-1.0.0.jar
cp target/gamification-impl-1.0.0.jar ../docker/images/springboot/apps
cd ../docker

cd ../gamification-specs
mvn install
rm -rf ../docker/images/mvnruntest/gamification-specs
cp -r ./ ../docker/images/mvnruntest/gamification-specs

cd ../docker/topologietest
docker-compose build --no-cache
