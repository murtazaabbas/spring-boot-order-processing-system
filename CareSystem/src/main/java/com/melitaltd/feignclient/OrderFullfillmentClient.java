package com.melitaltd.feignclient;

import com.melitaltd.entity.OrderEntity;
import com.melitaltd.model.Order;
import com.melitaltd.model.OrderFullFillmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderfullfillmentclient", url = "${order.fullfillment.service.url}")
public interface OrderFullfillmentClient {
    @PostMapping("/fullfill-order")
    OrderFullFillmentResponse fullFillOrder(@RequestBody OrderEntity order);
}
