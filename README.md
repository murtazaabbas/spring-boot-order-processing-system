# spring-boot-orderEntity-processing-system
orderEntity service will publish the orderEntity to care system and through care system orders will be approved by admin and will be submitted to orderEntity fulfillment service.

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

Checkout the dev branch and run the below mentioned command in each of the module:

````
mvn clean build
````

1) First we need to run the config server service and rabbit mq server.
Once the service is up then check config server is returning the properties properly according to the each service 
````
http://localhost:8888/care-system/dev
http://localhost:8888/orderEntity-service/dev
````

2) Run "order service" and "care system server"

Also check rabbit mq web interface  http://localhost:15672/