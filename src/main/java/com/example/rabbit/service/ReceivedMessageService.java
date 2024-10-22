package com.example.rabbit.service;

import com.example.rabbit.dto.BrokerMessage;
import com.example.rabbit.entity.BrokerMessageEntity;
import com.example.rabbit.repository.ReceivedMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by vladimirsabo on 22.10.2024
 */
@Service
@RequiredArgsConstructor
public class ReceivedMessageService {
    private final ReceivedMessageRepository repository;

    public Long saveMessage(BrokerMessage message) {
        var entity = new BrokerMessageEntity();
        BrokerMessageEntity.BodyMessageEntity body = new BrokerMessageEntity.BodyMessageEntity(message.getBody().getBody());
        entity.setBody(body);
        entity.setQueue(message.getQueue());
        var savedEntity = repository.save(entity);
        return savedEntity.getId();
    }
}
