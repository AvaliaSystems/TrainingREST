#!/bin/bash
./runREST_API.sh

cd ../gamification-specs

../scripts/wait-for-it.sh -t 30 localhost:8080 -- mvn clean package verify
