package com.example.rabbit.dto;

import java.io.Serializable;

/**
 * Created by vladimirsabo on 17.10.2024
 */
public interface BodyMessage extends Serializable {
    String getBody();

    String getExtension();
}
