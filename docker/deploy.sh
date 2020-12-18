#!/bin/sh
cd ..
readonly BASE_DIR="$PWD"
cd gamification-impl/
mvn install
rm -f "$BASE_DIR"/docker/images/springboot/apps/gamification-impl-1.0.0.jar
cp target/gamification-impl-1.0.0.jar "$BASE_DIR"/docker/images/springboot/apps

cd "$BASE_DIR"/gamification-specs
rm -rf "$BASE_DIR"/docker/images/failsafe/pom.xml
rm -rf "$BASE_DIR"/docker/images/failsafe/src
cp -r ./pom.xml "$BASE_DIR"/docker/images/failsafe/pom.xml
cp -r ./src "$BASE_DIR"/docker/images/failsafe/src
cd "$BASE_DIR"/docker
