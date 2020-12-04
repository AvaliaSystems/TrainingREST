#!/bin/bash
echo "======================================="
echo "=== Running REST API latest version ==="
echo "======================================="
docker pull ghcr.io/amt-project/trainingrest/apirest:latest
docker-compose down -v
docker-compose up
