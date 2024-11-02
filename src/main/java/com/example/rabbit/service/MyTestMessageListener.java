package com.example.rabbit.service;

import com.example.rabbit.dto.BodyMessage;
import com.example.rabbit.entity.AfterReceiveEntity;
import com.example.rabbit.entity.ReceivedMessagesEntity;
import com.example.rabbit.repository.AfterReceiveRepository;
import com.example.rabbit.repository.ReceivedMessagesRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MyTestMessageListener {

    public static final long SLEEP_TIME_MILLIS = 1000L;
    @Value("${rabbitmq.exchange}")
    String exchange;
    private final ReceivedMessagesRepository receivedMessagesRepository;
    private final AfterReceiveRepository afterReceiveRepository;

    @RabbitListener(queues = {"${rabbitmq.queue1}", "${rabbitmq.queue2}"}, autoStartup = "true")
    public void handleMessage(BodyMessage message, Message originalMessage) throws InterruptedException {
        log.info("processing...");
        Thread.sleep(SLEEP_TIME_MILLIS);
        MessageProperties properties = originalMessage.getMessageProperties();
        var queueName = properties.getConsumerQueue();
        var headers = properties.getHeaders();
        log.info("Received message from queue {}: \n{} with headers:\n{}", queueName, message, headers);
        try {
            //сохраним сырое сообщение до обработки бизнес-логики, которая может вызвать эксепшн.
            Long receivedMessageId = saveReceivedMessage(originalMessage, properties);
            //обработка сообщения и сохранение обработанного сообщения
            Long afterReceiveId = someBusinessLogicCanThrowException(properties, message, receivedMessageId);
            //обновление записи выше (сырых вычитанных сообщений) с добавлением айдишника на таблицу обработанных
            updateReceivedMessage(receivedMessageId, afterReceiveId);
            //с добавлением айдишника-ссылки на таблицу обработанных.
        } catch (RuntimeException e) {
            log.error("some processing error,\n{}", e.getMessage());
            throw new AmqpRejectAndDontRequeueException(e); //убирает возврат сообщения в очередь
        }
    }

    private Long saveReceivedMessage(Message originalMessage, MessageProperties properties) {
        ReceivedMessagesEntity entity = new ReceivedMessagesEntity();
        entity.setRoutingKey(properties.getReceivedRoutingKey());
        entity.setSerializedBody(originalMessage.getBody());
        ReceivedMessagesEntity saved = receivedMessagesRepository.save(entity);
        log.info("save ReceivedMessagesEntity = {}", saved);
        return saved.getId();
    }

    private void updateReceivedMessage(Long idReceivedMessage, Long idAfterReceiveMessage) {
        ReceivedMessagesEntity toUpdate = receivedMessagesRepository.findById(idReceivedMessage)
                .orElseThrow(() ->
                        new RuntimeException("Can't find raw message by id = %s".formatted(idReceivedMessage)));
        toUpdate.setAfterReceiveEntityId(idAfterReceiveMessage);
        receivedMessagesRepository.save(toUpdate);
    }

    private Long someBusinessLogicCanThrowException(MessageProperties properties, BodyMessage message,
                                                    Long receivedMessageId) {
        if (properties.getDeliveryTag() % 2 == 0) {
            throw new RuntimeException("processing exception"); //бесконечно перечитывает если не хендлить
        }
        AfterReceiveEntity entity = new AfterReceiveEntity();
        entity.setReceivedMessagesEntityId(receivedMessageId);
        entity.setSomeImportantInfo(message.getBody());
        var saved = afterReceiveRepository.save(entity);
        log.info("save AfterReceiveEntity = {}", saved);
        return saved.getId();
    }
}
