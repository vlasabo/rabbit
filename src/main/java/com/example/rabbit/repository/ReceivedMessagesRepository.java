package com.example.rabbit.repository;

import com.example.rabbit.entity.ReceivedMessagesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vladimirsabo on 01.11.2024
 */
@Repository
public interface ReceivedMessagesRepository extends CrudRepository<ReceivedMessagesEntity, Long> {
}
