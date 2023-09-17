package com.melitaltd.amq;

import com.melitaltd.model.Order;
import com.melitaltd.services.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderConsumer {

    private final OrderService orderService;

    public OrderConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(
            queues = "care.system", concurrency = "1"
    )
    public void receiveMessage(Order order, Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        log.info("Message Received: {}", order);
        if(order.getTraceId() == null){
            log.warn("Invalid request received: {}");
            channel.basicNack(tag, false, true);
        } else{
            CompletableFuture<Boolean> response = orderService.processMQOrder(order);
            if (response.get()) {
                channel.basicAck(tag, false);
            } else {
                channel.basicNack(tag, false, true);
            }
        }
    }
}
