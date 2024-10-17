package com.example.rabbit.controller;

import com.example.rabbit.dto.MyTestBrokerMessage;
import com.example.rabbit.service.SendMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vladimirsabo on 17.10.2024
 */
@RestController("/send")
@RequiredArgsConstructor
public class SendNotificationToQueueController {
    private final SendMessageService sendMessageService;

    @PostMapping("/test")
    public ResponseEntity<String> sendTestMessage(@Valid @RequestBody MyTestBrokerMessage message) {
        return new ResponseEntity<>(sendMessageService.sendMessage(message), HttpStatus.CREATED);
    }
}
