#!/usr/bin/env bash

docker run -it --rm --name test-maven-container -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven maven:3-jdk-8 mvn test