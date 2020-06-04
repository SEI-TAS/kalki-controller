#!/usr/bin/env bash
export CMD_PARAMS="$@"
export HOST_TZ=$(cat /etc/timezone)
docker-compose up -d
bash compose_logs.sh
