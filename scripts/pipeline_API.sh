#!/bin/bash
./runREST_API.sh

./wait-for-it.sh apirest:8080 -- cd ../gamification-specs && mvn clean package verify
