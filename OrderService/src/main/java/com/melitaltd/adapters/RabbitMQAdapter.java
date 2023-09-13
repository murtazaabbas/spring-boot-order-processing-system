package com.melitaltd.adapters;

import com.melitaltd.config.AppProperties;
import com.melitaltd.models.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQAdapter {
    private final RabbitTemplate rabbitTemplate;

    private final AppProperties properties;

    public RabbitMQAdapter(RabbitTemplate rabbitTemplate, AppProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void sendRequestMessage(Order message) {
        rabbitTemplate.convertAndSend(
                properties.getDirectExchange(),
                properties.getDirectQueue(),
                message
        );
    }
}
