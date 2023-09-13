package com.melitaltd.services;


import com.melitaltd.adapters.RabbitMQAdapter;
import com.melitaltd.exception.ServiceError;
import com.melitaltd.models.Order;
import com.melitaltd.models.OrderRequest;
import com.melitaltd.models.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderService {
    private final RabbitMQAdapter adapter;
    private final ModelMapper modelMapper;

    public OrderService(RabbitMQAdapter adapter, ModelMapper modelMapper) {
        this.adapter = adapter;
        this.modelMapper = modelMapper;
    }

    public OrderResponse sendRequestMessage(OrderRequest orderRequest){
        try {
            System.out.println("Sending message: "+ orderRequest.toString());
            Order order = modelMapper.map(orderRequest, Order.class);
            String orderId = UUID.randomUUID().toString();
            order.setId(orderId);
            adapter.sendRequestMessage(order);
            log.info("Sign-ip request has been received");
            return new OrderResponse(orderId);
        } catch (Exception e) {
            throw ServiceError.INTERNAL_SERVER_ERROR.buildException(e.getMessage());
        }
    }
}
