#! /bin/bash.sh
git fetch && git reset --hard origin/master
ENV=prod PASSWORD=4fsd5fsd ROOT_PASSWORD=f4s65f4sd docker-compose up -d spring --build
