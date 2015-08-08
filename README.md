# example-restful-project
Attempt to create the most sophisticated example of RESTful application with
Spring.

## About
Well! From functional point of view, this is just manager of teapots. And even
not of real ones but of simulators. Not very useful. So this application no
less no more than a demo to show how to build modern Restful Applications on the
the stack Java(Spring) & JavaScript(AngularJs) according to the best
practices.  
So what practices shown here:  
**1. Full separation of Java (server side) & JavaScript (client side)**  
**2. All controllers are Restful**  
No JSP (or other server side templates) are used. Server side charge about
business logic and all presentation is done on the client side.  
**3. Cloud oriented**  
Now runs on Heroku, later will be able to run on Cloud Foundry.

## Features
- Java 8
- Pure Java-based configuration
- Spring Boot(on Undertow)
- Spring MVC (Restful)
- Spring WebSocket
- Springfox (Swagger)
- MongoDB
- Cloud oriented (Heroku)
- Docker

## Demo
Demo runs on [Heroku](https://www.heroku.com/).  
Find Swagger UI [here](http://petstore.swagger.io/?url=https://example-restful-project.herokuapp.com/v2/api-docs).

## Create self-contained distribution
To create Docker image with Java, application binaries and MongoDB ready to run do :
```sh
docker build -t hosuaby/restful .
```
After launch it :
```sh
docker run --name restful -d -p 8080:8080 -p 27017:27017 hosuaby/restful
```
Web server is accessible on port `8080` and mongo on `27017` of `localhost`.

## Development environment
Development environment is also provided as docker containers. To setup it run :
```sh
docker-compose up -d
```
You can modify the source code in your IDE and save it. If Automatic Build is
enabled project will be automatically rebuilt and reloaded within container.
