#!/bin/bash
./runREST_API.sh

#../scripts/wait-for-it.sh -t 70 apirest:8080 --

docker build --tag bashwait ../docker/bashwait
docker run --network=project_2_web -i bashwait bash -c "./wait-for-it.sh -t 50 apirest:8080 && exit"

cd ../gamification-specs

mvn clean package verify
