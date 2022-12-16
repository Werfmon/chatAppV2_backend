#! /bin/bash.sh
docker-compose down
git fetch && git reset --hard origin/master
git pull origin master
ENV=prod PASSWORD=4fsd5fsd ROOT_PASSWORD=f4s65f4sd docker-compose build
ENV=prod PASSWORD=4fsd5fsd ROOT_PASSWORD=f4s65f4sd docker-compose up -d
