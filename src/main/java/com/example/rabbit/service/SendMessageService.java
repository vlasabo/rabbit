package com.example.rabbit.service;

import com.example.rabbit.dto.BrokerMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vladimirsabo on 17.10.2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SendMessageService {
    private final EnqueueDequeService enqueueDequeService;
    private final SendingMessageService sendingMessageService;

    @Transactional
    public Long sendMessage(BrokerMessage brokerMessage) {
        log.info("try to send message {} to queue {}", brokerMessage.getBody(), brokerMessage.getQueue());
        Long id = sendingMessageService.saveMessage(brokerMessage);
        enqueueDequeService.publishMessage(brokerMessage.getBody());
        return id;
    }
}
