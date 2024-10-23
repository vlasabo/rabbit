package com.example.rabbit.dto;

/**
 * Created by vladimirsabo on 17.10.2024
 */
public interface BrokerMessage {
    String getRoutingKey();

    BodyMessage getBody();
}
