package com.melitaltd.services;


import com.melitaltd.amq.OrderProducer;
import com.melitaltd.exception.ServiceError;
import com.melitaltd.model.Order;
import com.melitaltd.model.QueueObjectWrapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class OrderService {
    private final OrderProducer orderProducer;
    private final ModelMapper modelMapper;

    public OrderService(OrderProducer orderProducer, ModelMapper modelMapper) {
        this.orderProducer = orderProducer;
        this.modelMapper = modelMapper;
    }

    public boolean sendRequestMessage(Order order, String traceId) {
        Optional.ofNullable(order)
                .ifPresentOrElse(
                        data -> {
                            QueueObjectWrapper queueObjectWrapper = new QueueObjectWrapper(traceId, order);
                            log.info("Sending queueObjectWrapper: " + queueObjectWrapper);
                            orderProducer.sendRequestMessage(queueObjectWrapper);
                        },
                        () -> {
                            throw ServiceError.INTERNAL_SERVER_ERROR.buildException();
                        }
                );
        return true;
    }
}
