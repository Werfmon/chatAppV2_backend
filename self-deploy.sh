#! /bin/bash.sh 

ENV=prod docker-compose build
docker-compose up -d
