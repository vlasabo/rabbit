package com.example.rabbit.service;

import com.example.rabbit.dto.BodyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
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

    public static final long SLEEP_TIME_MILLIS = 1000L;
    @Value("${rabbitmq.exchange}")
    String exchange;

    @RabbitListener(queues = {"${rabbitmq.queue1}", "${rabbitmq.queue2}"}, autoStartup = "true")
    public void handleMessage(BodyMessage message, Message originalMessage) throws InterruptedException {
        log.info("processing...");
        Thread.sleep(SLEEP_TIME_MILLIS);
        MessageProperties properties = originalMessage.getMessageProperties();
        var queueName = properties.getConsumerQueue();
        var headers = properties.getHeaders();
        log.info("Received message from queue {}: \n{} with headers:\n{}", queueName, message, headers);
        try {
            someBusinessLogicCanThrowException(properties);
        } catch (RuntimeException e) {
            log.error("some processing error,\n{}", e.getMessage());
            throw new AmqpRejectAndDontRequeueException(e); //убирает возврат сообщения в очередь
        }
    }

    private void someBusinessLogicCanThrowException(MessageProperties properties) {
        if (properties.getDeliveryTag() % 2 == 0) {
            throw new RuntimeException("processing exception"); //бесконечно перечитывает если не хендлить
        }
    }
}
