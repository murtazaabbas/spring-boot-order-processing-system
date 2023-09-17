# spring-boot-orderEntity-processing-system
orderEntity service will publish the orderEntity to care system and through care system orders will be approved by admin and will be submitted to orderEntity fulfillment service.

---
## How to run

1) First we need to run the config server service.
Once the service is up then check is it returning the properties properly 
````
http://localhost:8888/care-system/dev
http://localhost:8888/orderEntity-service/dev
````

2) Then run the rabbitmq docker.

````
docker-compose -f /path/to/your/docker-compose.yml up
````
Also check rabbit mq web interface  http://localhost:15672/

3) After that all the remaining services like OrderService and CareSystem