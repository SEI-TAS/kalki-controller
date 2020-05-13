#!/usr/bin/env bash
docker stop kalki-postgres-test
docker run -p 5433:5432 --rm -d --name kalki-postgres-test kalki/kalki-postgres-test
docker build --network=host -t kalki/kalki-main-controller .
docker stop kalki-postgres-test
