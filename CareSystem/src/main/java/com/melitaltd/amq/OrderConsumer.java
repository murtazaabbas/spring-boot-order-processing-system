package com.melitaltd.amq;

import com.melitaltd.model.Order;
import com.melitaltd.model.QueueObjectWrapper;
import com.melitaltd.services.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class OrderConsumer {

    private final OrderService orderService;
    private final ModelMapper mapper;

    public OrderConsumer(OrderService orderService, ModelMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @RabbitListener(
            queues = "care.system", concurrency = "1"
    )
    public void receiveMessage(QueueObjectWrapper message, Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        log.info("Message Received: {}", message);
        if(message.getTraceId() == null || message.getObject() == null){
            channel.basicNack(tag, false, true);
        } else{
            Order order = mapper.map(message.getObject(), Order.class);
            CompletableFuture<Boolean> response = orderService.processMQOrder(order, message.getTraceId());

            try {
                Boolean result = response.orTimeout(5, TimeUnit.SECONDS).get();
                if (result) {
                    channel.basicAck(tag, false);
                } else {
                    channel.basicNack(tag, false, true);
                }
            } catch (Exception e) {
                channel.basicNack(tag, false, true);
                log.error(String.valueOf(e));
            }
        }
    }
}
