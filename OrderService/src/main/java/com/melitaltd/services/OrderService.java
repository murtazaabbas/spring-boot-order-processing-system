package com.melitaltd.services;


import com.melitaltd.amq.OrderProducer;
import com.melitaltd.exception.ServiceError;
import com.melitaltd.model.Order;
import com.melitaltd.model.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderService {
    private final OrderProducer orderProducer;
    private final ModelMapper modelMapper;

    public OrderService(OrderProducer orderProducer, ModelMapper modelMapper) {
        this.orderProducer = orderProducer;
        this.modelMapper = modelMapper;
    }

    public Order sendRequestMessage(OrderRequest orderRequest) {
        try {
            Order order = modelMapper.map(orderRequest, Order.class);
            order.setTraceId(UUID.randomUUID().toString());
            log.info("Sending message: " + order);
            orderProducer.sendRequestMessage(order);
            return order;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw ServiceError.INTERNAL_SERVER_ERROR.buildException(e.getMessage());
        }
    }
}
