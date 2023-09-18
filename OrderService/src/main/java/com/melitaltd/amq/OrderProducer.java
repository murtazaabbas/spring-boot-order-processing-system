package com.melitaltd.amq;

import com.melitaltd.config.RabbitMQProperties;
import com.melitaltd.model.QueueObjectWrapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQProperties properties;

    public OrderProducer(RabbitTemplate rabbitTemplate, RabbitMQProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void sendRequestMessage(QueueObjectWrapper message) {
        rabbitTemplate.convertAndSend(
                properties.getExchange(),
                properties.getRoutingkey(),
                message
        );
    }
}
