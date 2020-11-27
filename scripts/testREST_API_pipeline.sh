#!/bin/bash
./runREST_API.sh &
sleep 60 # yes, it's pretty thrashy but this will do for now

echo "========================"
echo "=== Testing REST API ==="
echo "========================"
cd ../gamification-specs
mvn clean package test
