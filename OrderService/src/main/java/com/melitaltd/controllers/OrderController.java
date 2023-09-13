package com.melitaltd.controllers;

import com.melitaltd.models.OrderRequest;
import com.melitaltd.models.OrderResponse;
import com.melitaltd.services.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.melitaltd.StartApplication.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1 + "/orderservice")
@Validated
@Slf4j
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<?> order(@Valid @RequestBody OrderRequest order){
        OrderResponse orderResponse = this.orderService.sendRequestMessage(order);
        return ResponseEntity.ok(orderResponse);
    }
}
