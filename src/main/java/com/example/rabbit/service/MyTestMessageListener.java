package com.example.rabbit.service;

import com.example.rabbit.dto.BodyMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by vladimirsabo on 23.10.2024
 */
@Service
public class MyTestMessageListener {
    @Value("${rabbitmq.queue}")
    String queueName;

    @Value("${rabbitmq.exchange}")
    String exchange;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleMessage(BodyMessage message) {
        System.out.println("Received message from queue " + queueName + ": " + message);
    }
}
