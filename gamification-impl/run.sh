#!/bin/bash

docker-compose -f ../docker/topologies/docker-compose.yaml down

docker-compose -f ../docker/topologies/docker-compose.yaml up --detach amtdb amtadminer

mvn spring-boot:run
