package com.example.rabbit.entity;

import com.example.rabbit.dto.BodyMessage;
import com.example.rabbit.dto.BrokerMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * Created by vladimirsabo on 22.10.2024
 */
@Entity
@Getter
@Setter
public class BrokerMessageEntity implements BrokerMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String routingKey;
    @Embedded
    private BodyMessageEntity body;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class BodyMessageEntity implements BodyMessage {
        @NotNull
        String body;
        @NotNull
        String extension;
    }
}
