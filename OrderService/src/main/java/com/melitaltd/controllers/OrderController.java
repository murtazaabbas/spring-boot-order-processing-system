package com.melitaltd.controllers;

import com.melitaltd.model.Order;
import com.melitaltd.services.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.melitaltd.StartOrderServiceApplication.API_VERSION_1;

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
    public ResponseEntity<?> order(@NotNull @NotEmpty @RequestHeader("TRACE-ID") String traceId,
                                   @Valid @RequestBody Order order) {
        this.orderService.sendRequestMessage(order, traceId);
        return ResponseEntity.ok().build();
    }

}
