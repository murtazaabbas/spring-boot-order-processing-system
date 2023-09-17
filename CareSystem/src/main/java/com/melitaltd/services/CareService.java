package com.melitaltd.services;

import com.melitaltd.entity.OrderEntity;
import com.melitaltd.exception.ServiceError;
import com.melitaltd.model.ApprovalRequest;
import com.melitaltd.model.Order;
import com.melitaltd.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CareService {

    private final EmailService emailService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    public CareService(EmailService emailService, OrderRepository orderRepository, ModelMapper modelMapper){
        this.emailService = emailService;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public List<Order> getOrdersByApprovalStatus(int approvalStatus){
        AtomicReference<List<Order>> orderRef = new AtomicReference<>();
        orderRepository.findByApprove(approvalStatus).ifPresentOrElse(
                orders -> {
                    List<Order> orderList = orders.stream()
                            .map(source -> modelMapper.map(source, Order.class))
                            .collect(Collectors.toList());
                    orderRef.set(orderList);

                },
                () -> {
                    throw ServiceError.DATA_NOT_AVAILABLE.buildException();
                }
        );
        return orderRef.get();
    }

    public void approveOrder(int orderId, ApprovalRequest approvalRequest){
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> ServiceError.DATA_NOT_AVAILABLE.buildException());
        orderEntity.setApprove(approvalRequest.getApprovalStatus());
        orderRepository.saveAndFlush(orderEntity);
    }
}
