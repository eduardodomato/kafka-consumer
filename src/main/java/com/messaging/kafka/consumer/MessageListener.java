package com.messaging.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messaging.kafka.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageListener {

    private final ObjectMapper objectMapper;

    public MessageListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RetryableTopic(attempts = "4")
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


    @DltHandler
    public void handleDlt(String message, Exception exception,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        // Log DLT arrival with context
        if (exception != null) {
            // Log full exception (stacktrace) as the last parameter so SLF4J prints it
            log.error("Message sent to DLT from topic '{}'. Payload: {}.", topic, message, exception);
        } else {
            log.error("Message sent to DLT from topic '{}'. Payload: {}.", topic, message);
        }

        // Try to deserialize for additional context
        try {
            Customer customer = objectMapper.readValue(message, Customer.class);
            log.error("DLT payload parsed as Customer: {}", customer);
        } catch (Exception e) {
            log.error("DLT payload is not a Customer, raw payload: {}", message);
        }
    }

}
