#!/bin/bash
./runREST_API.sh

echo "========================"
echo "=== Testing REST API ==="
echo "========================"
docker wait apirest
cd ../fruits-specs
mvn clean test
