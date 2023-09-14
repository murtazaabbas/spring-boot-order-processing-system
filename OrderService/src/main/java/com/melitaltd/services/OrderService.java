package com.melitaltd.services;


import com.melitaltd.amq.RabbitMQProducer;
import com.melitaltd.exception.ServiceError;
import com.melitaltd.model.Order;
import com.melitaltd.model.OrderRequest;
import com.melitaltd.model.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderService {
    private final RabbitMQProducer adapter;
    private final ModelMapper modelMapper;

    public OrderService(RabbitMQProducer adapter, ModelMapper modelMapper) {
        this.adapter = adapter;
        this.modelMapper = modelMapper;
    }

    public OrderResponse sendRequestMessage(OrderRequest orderRequest){
        try {
            Order order = modelMapper.map(orderRequest, Order.class);
            String orderId = UUID.randomUUID().toString();
            order.setId(orderId);
            log.info("Sending message: "+ order.toString());
            adapter.sendRequestMessage(order);
            return new OrderResponse(orderId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw ServiceError.INTERNAL_SERVER_ERROR.buildException(e.getMessage());
        }
    }
}
