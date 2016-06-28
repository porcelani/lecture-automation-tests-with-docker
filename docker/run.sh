#!/usr/bin/env bash


docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker rmi wildfly-container
docker rmi postgres-container
docker-compose up --build
