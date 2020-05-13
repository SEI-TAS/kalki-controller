#!/usr/bin/env bash
docker run -p 5433:5432 --rm -d --name kalki-postgres-test kalki/kalki-postgres-test
