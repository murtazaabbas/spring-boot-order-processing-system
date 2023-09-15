package com.melitaltd.amq;

import com.melitaltd.model.Order;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageReceiver {

    @RabbitListener(
            queues = "#{completeQueue.name}",
            containerFactory = "rabbitListenerManualContainerFactory"
    )
    public void receiveMessage(Order order, Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        log.info("Message Received: ", order);
        if(order.getId() == null){
            // Error path
            channel.basicNack(tag, false, true);
        } else{
            Thread.sleep(10000);
            System.out.println("Listener method executed successfully");
            channel.basicAck(tag, false);
        }
    }
}
