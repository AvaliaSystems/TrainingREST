## Technical detail (Prerequisites)

Our application is build over the Jakarta EE standard (Java EE). We are using the following tools/technology to bring this project up:)
The following prerequisites must be installed to run this project.

| Component                     | Name                                                      | Version      |
| ----------------------------- | --------------------------------------------------------- | ------------ |
| Web FrameWork                 | [Spring-Boot](https://spring.io/projects/spring-boot)     | __>=2.3__    |
| IDE                           | [IntelliJ Idea Ultimate](https://www.jetbrains.com/idea/) | __>=2020.2__ |
| Build/dependency Manager      | [Maven](https://maven.apache.org/)                        | __>= 3.6__   |
| testing tools                 | [cucumber](https://cucumber.io/)                          | __>=2.6__    |
| Containerization tools        | [Docker](https://www.docker.com/)                         | __>= 19.03__ |
| Docker compose                | [Docker](https://docs.docker.com/compose/)                         | __>= 19.03__ |


## Run the project
### Clone the repository
```sh
# Clone the repository
git clone https://github.com/Gusamaal/Gamification-Engine-AMT.git

# Move to the cloned direcory
cd Gamification-Engine-AMT

# Execute the deployment sh script
cd docker/prod_topologie
docker-compose pull
docker-compose up
```

## Build and run the Gamification MicroService

You can use maven to build and run the REST API implementation from the command line. After invoking the following maven goal, the Spring Boot server will be up and running, listening for connections on port 8086.

```
cd gamification-impl/
cp ../.envexample .env
docker-compose -f ./docker/topologies/docker-compose.yaml up -d amtdb amtadminer
mvn spring-boot:run
```

## You can then access:

* the [API documentation](http://localhost:8086/swagger-ui.html), generated from annotations in the code
* the [API endpoint](http://localhost:8086/), accepting GET and POST requests

You can use curl to invoke the endpoints:

# Test the Gamification microservice by running the executable specification

You can use the Cucumber project to validate the API implementation. Do this when the server is running.

```
cd gamification-specs/
cp ../.envexample .env
mvn clean test
```
You will see the test results in the console, but you can also open the file located in `./target/cucumber`

