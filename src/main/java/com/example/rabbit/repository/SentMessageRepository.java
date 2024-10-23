package com.example.rabbit.repository;

import com.example.rabbit.entity.BrokerMessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vladimirsabo on 22.10.2024
 */
@Repository
public interface SentMessageRepository extends CrudRepository<BrokerMessageEntity, Long> {
}
