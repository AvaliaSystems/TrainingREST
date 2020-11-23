#!/bin/bash

docker-compose up -d database
./wait-for-it.sh localhost:3306
docker-compose up
