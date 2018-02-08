# Data Processing Pipeline Demo Project


## What is this?
- This is a sample data processing pipeline implementation. Taking a dummy JSON as an input and puts the given payload on a running `Redis` server. Another consumer is running in the application taking the freshly received message and persists it in a `MariaDB` instance. Furthermore, A REST endpoint is implemented for retrieving all the messages persisted in JSON format from the database.


## REST Endpoint

| Method | Path      | Description                              | Available from UI |
|--------|-----------|------------------------------------------|-------------------|
| GET    | /endpoint | Get all persisted messages from database | ✔                 |
| POST   | /endpoint | Send new message to server               | ✔                 |


## Getting Started
Following the instructions will get you a copy of the project up and running on your local machine for development and testing purposes


## Prerequisites
- [Docker CE](https://docs.docker.com/install)
- [Docker Compose](https://docs.docker.com/compose/install/)


## Used Technologies
- Java
- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) - The project foundation
- [Redis](https://redis.io/documentation) - Message Broker
- [MariaDB](https://mariadb.org/) - Database
- [Maven](http://maven.apache.org/guides/) - Build & Dependency Management
- [Docker](https://docs.docker.com/) - Virtualization


## How to Test?
- Clone project to an empty directory
- Run command `docker-compose up -d`

Example output:
```sh
$ docker-compose  up -d
......
Starting demo-redis ...
Starting demo-redis ... done
Starting demo-app ... done
.......
.......
demo-app   | [INFO] --- spring-boot-maven-plugin:1.5.10.RELEASE:run (default-cli) @ data-processing-pipeline-demo ---
demo-app   |
demo-app   |   .   ____          _            __ _ _
demo-app   |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
demo-app   | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
demo-app   |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
demo-app   |   '  |____| .__|_| |_|_| |_\__, | / / / /
demo-app   |  =========|_|==============|___/=/_/_/_/
demo-app   |  :: Spring Boot ::       (v1.5.10.RELEASE)

```

- Access to `http://localhost:9000`
- Alternativly, test with `Curl` by running:
> curl -X POST http://localhost:9000/endpoint -H 'Content-Type: application/json' -d "{"content":"sample message"}"


## Expected fails

- Cannot access `http://localhost:9000`

Run `$ docker container inspect demo-app` to show the docker container `IP`

 ```sh
 $ docker container demo-app inspect
 ....
 ....
 "Networks": {
                "dataprocessingpipeline_default": {
                    ....
                    ....
                    "IPAddress": "172.21.0.4",
                    ....
                    ....
                }
            }
 ```
 - `Bind for 0.0.0.0:[port] failed: port is already allocated`.

 As it says, the specified port is occupied by another process. Change the port in `docker-compse.yml` or stop the process using the port

 - Test fail due to `java.net.UnknownHostException: [host]`

Change the mariadb & redis host on `application.properties`
