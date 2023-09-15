package com.melitaltd.controllers;

import com.melitaltd.model.Order;
import com.melitaltd.model.OrderRequest;
import com.melitaltd.services.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> order(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = this.orderService.sendRequestMessage(orderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("hello world");
    }

}
