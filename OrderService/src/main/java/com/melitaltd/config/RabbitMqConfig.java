package com.melitaltd.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    private final RabbitMQProperties properties;
    private final ConnectionFactory connectionFactory;

    public RabbitMqConfig(RabbitMQProperties properties, ConnectionFactory connectionFactory) {

        this.properties = properties;
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public Exchange directExchange() {
        // direct exchange/queue
        return new DirectExchange(properties.getExchange(), true, false);
    }

    @Bean
    public Queue queue() {
        return new Queue(properties.getQueue());
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(directExchange())
                .with(properties.getRoutingkey()).noargs();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(ObjectMapper mapper) {
        var converter = new Jackson2JsonMessageConverter(mapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory, ObjectMapper objectMapper) {
        RabbitTemplate template = new RabbitTemplate();
        template.setConnectionFactory(factory);
        template.setMessageConverter(messageConverter(objectMapper));
        return template;
    }


}
