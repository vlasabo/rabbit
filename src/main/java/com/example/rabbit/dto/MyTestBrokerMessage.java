package com.example.rabbit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Created by vladimirsabo on 17.10.2024
 */
@Builder
@Data
public class MyTestBrokerMessage implements BrokerMessage {
    @NotBlank
    private String queue;
    @NotBlank
    private TestBodyMessage body;

    @Data
    private static class TestBodyMessage implements BodyMessage {
        @NotNull
        String body;
    }
}
