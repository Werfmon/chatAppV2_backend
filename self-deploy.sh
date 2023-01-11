#! /bin/bash.sh
git fetch && git reset --hard origin/master
ENV=prod PASSWORD=$1 ROOT_PASSWORD=$1 docker-compose build
ENV=prod PASSWORD=$1 ROOT_PASSWORD=$1 docker-compose up -d
