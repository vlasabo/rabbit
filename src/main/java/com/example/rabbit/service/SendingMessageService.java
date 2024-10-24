package com.example.rabbit.service;

import com.example.rabbit.dto.BrokerMessage;
import com.example.rabbit.entity.BrokerMessageEntity;
import com.example.rabbit.repository.SentMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by vladimirsabo on 22.10.2024
 */
@Service
@RequiredArgsConstructor
public class SendingMessageService {
    private final SentMessageRepository repository;

    public Long saveMessage(BrokerMessage message) {
        var entity = new BrokerMessageEntity();
        var body = new BrokerMessageEntity.BodyMessageEntity(
                message.getBody().getBody(),
                message.getBody().getExtension()
        );
        entity.setBody(body);
        entity.setRoutingKey(message.getRoutingKey());
        var savedEntity = repository.save(entity);
        return savedEntity.getId();
    }
}
