package com.example.rabbit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vladimirsabo on 01.11.2024
 */
@Entity
@Getter
@Setter
public class ReceivedMessagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String routingKey;
    private byte[] serializedBody;
    private Long afterReceiveEntityId;
}
