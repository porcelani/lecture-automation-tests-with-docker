#Lecture: Automation tests with docker
https://docs.google.com/presentation/d/1Z5GZP-XOsQej9cpJ43Dp8-UXAWxxFFNmVsFS58ubERk/edit?usp=sharing

It's a simple Java project to show how can be easy to run your enviroment with docker and tests. 

OBS:The project configuration is prepared to use with Docker Toolbox. 

###Step 1: Build you project
```
cd .../lecture-automation-tests-with-docker/site
mvn clean install 
```

###Step 2: Start containers
```
docker-compose up
```

###Step 3: Run you selenium test
```
cd .../lecture-automation-tests-with-docker/site-tests
mvn test
```
