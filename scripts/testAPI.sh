#!/bin/bash
./runREST_API.sh

echo "========================"
echo "=== Testing REST API ==="
echo "========================"
sleep 10 # wait for API to start
cd ../fruits-specs
mvn clean test

