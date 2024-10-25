package com.example.rabbit.service;

import com.example.rabbit.dto.BodyMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by vladimirsabo on 22.10.2024
 */
@Service
public class EnqueueDequeService {
    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.exchange}")
    String exchange;


    public EnqueueDequeService(RabbitTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void publishMessage(String routingKey, BodyMessage bodyMessage) {
        amqpTemplate.convertAndSend(exchange, routingKey, bodyMessage);
    }
}
