package com.example.rabbit.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by vladimirsabo on 17.10.2024
 */
@Data
public class MyTestBrokerMessage implements BrokerMessage {
    @NotBlank
    private String routingKey;
    @NotBlank
    private TestBodyMessage body;

    @Data
    private static class TestBodyMessage implements BodyMessage {
        @NotNull
        String body;
        @NotNull
        String extension;
    }
}
