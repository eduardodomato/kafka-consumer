package com.messaging.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messaging.kafka.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageListener {

    private final ObjectMapper objectMapper;

    public MessageListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "kafka-passthrough", groupId = "group-1")
    public void consume(String message) {
        log.info("Received message: {}", message);
        
        // Try to parse as Customer object
        try {
            Customer customer = objectMapper.readValue(message, Customer.class);
            log.info("Parsed as Customer: {}", customer);
        } catch (Exception e) {
            // If it's not a Customer, treat it as a plain string message
            log.info("Parsed as String message: {}", message);
        }
    }
}
