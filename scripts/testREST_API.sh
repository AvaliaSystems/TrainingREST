#!/bin/bash
./runREST_API.sh &
sleep 30

echo "========================"
echo "=== Testing REST API ==="
echo "========================"
cd ../gamification-specs
mvn clean package test
