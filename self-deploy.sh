#! /bin/bash.sh 
git pull origin master
ENV=prod docker-compose build
docker-compose up -d
