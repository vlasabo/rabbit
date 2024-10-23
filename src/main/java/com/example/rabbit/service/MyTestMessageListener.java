package com.example.rabbit.service;

import com.example.rabbit.dto.BodyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by vladimirsabo on 23.10.2024
 */
@Service
@Slf4j
public class MyTestMessageListener {

    @Value("${rabbitmq.exchange}")
    String exchange;

    @RabbitListener(queues = {"${rabbitmq.queue1}", "${rabbitmq.queue2}"}, autoStartup = "true")
    public void handleMessage(BodyMessage message, Message originalMessage) {
        MessageProperties properties = originalMessage.getMessageProperties();
        var queueName = properties.getConsumerQueue();
        var headers = properties.getHeaders();
        log.info("Received message from queue {}: \n{} with headers:\n{}", queueName, message, headers);
    }
}
