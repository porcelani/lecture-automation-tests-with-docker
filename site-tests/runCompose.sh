#!/usr/bin/env bash

#stop all container
 docker stop `docker ps -aq`
#remove all container
 docker rm `docker ps -aq`

# start container
 docker-compose up -d