package com.melitaltd.services;

import com.melitaltd.entity.OrderEntity;
import com.melitaltd.exception.ServiceError;
import com.melitaltd.model.ApprovalRequest;
import com.melitaltd.model.Order;
import com.melitaltd.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CareService {

    private final EmailService emailService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public CareService(EmailService emailService, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.emailService = emailService;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public List<Order> getOrdersByApprovalStatus(int approvalStatus) {
        AtomicReference<List<OrderEntity>> orderRef = new AtomicReference<>();
        if (approvalStatus < 0) {
            orderRef.set(orderRepository.findAll());
        } else {
            orderRepository.findByApprove(approvalStatus).ifPresentOrElse(
                    data -> {
                        orderRef.set(data);
                    },
                    () -> {
                        throw ServiceError.DATA_NOT_AVAILABLE.buildException();
                    }
            );
        }

        List<Order> orders = orderRef.get().stream()
                .map(source -> modelMapper.map(source, Order.class))
                .collect(Collectors.toList());

        return orders;
    }

    @Transactional
    public void approveOrder(int orderId, ApprovalRequest approvalRequest) {
        int approvalStatus = 2; // rejected
        if (approvalRequest.isApprovalStatus()) {
            // call order fullfillment service and update the
            approvalStatus = 1;
        }
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> ServiceError.DATA_NOT_AVAILABLE.buildException());
        orderEntity.setApprove(approvalStatus);
        orderRepository.save(orderEntity);
    }
}
