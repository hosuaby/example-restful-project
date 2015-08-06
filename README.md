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

## Run it locally
To run application locally you need to have `Docker` & `docker-compose` installed.  
Execute instructions:
```sh
git clone git@github.com:hosuaby/example-restful-project.git
docker-compose up -d
```
