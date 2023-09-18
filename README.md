# spring-boot-orderEntity-processing-system

order service will publish the order to care system and through care system orders will be approved by admin and will be
submitted to orderEntity fulfillment service.
we are also required to sent an email for every order received from queue sent by order service
---

## Project tools, frameworks

1. Java 17
2. Springboot 3
3. spring security
4. spring data jpa
5. spring cloud config server
6. spring feign client
7. spring amq
8. H2 database
9. Junit 5 along with mockito
10. dockers

## How to run

Checkout the branch and run the below mention command in each of the module so that maven can run test cases, compile
and build the jar file:

````
mvn clean build
````

## Manual Run

1) First we need to run the config server service and rabbit mq server.
   Once the service is up then check config server is returning the properties properly according to each service

````
http://localhost:8888/care-system/dev
http://localhost:8888/orderEntity-service/dev
````

2) Run "order service" and "care system server"

Also check rabbit mq web interface  http://localhost:15672/

Note: postman collection and docker-compose file has been added for the application/services testing

Also, Integration testing, Junit+Mockito for business logic and Business model test case has been added only for order
service for the demonstration.

For order fullfillment dummy fiegnclient has been created and comment out later to show how http web service can be call.