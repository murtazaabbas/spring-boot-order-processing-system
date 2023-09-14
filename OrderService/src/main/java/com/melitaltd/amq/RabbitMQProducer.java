package com.melitaltd.amq;

import com.melitaltd.config.RabbitMQProperties;
import com.melitaltd.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQProperties properties;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate, RabbitMQProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void sendRequestMessage(Order message) {
        rabbitTemplate.convertAndSend(
                properties.getExchange(),
                properties.getRoutingkey(),
                message
        );
    }
}
