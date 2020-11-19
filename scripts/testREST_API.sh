#!/bin/bash
./runREST_API.sh

echo "========================"
echo "=== Testing REST API ==="
echo "========================"
docker wait apirest
cd ../gamification-specs
mvn clean test
