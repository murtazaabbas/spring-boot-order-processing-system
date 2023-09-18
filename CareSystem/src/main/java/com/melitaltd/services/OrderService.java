package com.melitaltd.services;

import com.melitaltd.config.CommonProperty;
import com.melitaltd.entity.OrderEntity;
import com.melitaltd.model.Order;
import com.melitaltd.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final CommonProperty commonProperty;
    private final OrderEmailService orderEmailService;

    public OrderService(OrderRepository orderRepository,
                        ModelMapper modelMapper,
                        CommonProperty commonProperty, OrderEmailService orderEmailService){
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.commonProperty = commonProperty;
        this.orderEmailService = orderEmailService;
    }

    public CompletableFuture<Boolean> processMQOrder(Order order, String traceId){
        return CompletableFuture.supplyAsync(() -> {
            boolean manualApproval = order.getProducts().stream().anyMatch(
                    value -> commonProperty.getManualApproveProducts().contains(value.getProductPackage().getProduct()));
            OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
            if(manualApproval){
                orderEntity.setApprove(0);
            }
            orderEntity.setCreatedAt(LocalDateTime.now());
            OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
            orderEmailService.generateOrderEmail(savedOrderEntity, manualApproval);

            if (manualApproval) {
                return true;
            }

            CompletableFuture<Boolean> fulfillmentResult = CompletableFuture.supplyAsync(() -> {
//                orderFullfillmentClient.fullFillOrder(orderEntity);
                return true;
            });

            // Wait for the fulfillmentResult to complete and return its result.
            return fulfillmentResult.join();
        });
    }
}
