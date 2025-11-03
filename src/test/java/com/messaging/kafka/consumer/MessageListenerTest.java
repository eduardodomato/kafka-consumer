package com.messaging.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MessageListenerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageListener listener = new MessageListener(objectMapper);

    @Test
    void handleDlt_withValidCustomerJson_doesNotThrow() {
        String customerJson = "{\"id\":\"123\",\"name\":\"Alice\",\"email\":\"alice@example.com\",\"phone\":\"555-1234\"}";

        assertDoesNotThrow(() -> listener.handleDlt(customerJson, new RuntimeException("boom"), "kafka-passthrough"));
    }

    @Test
    void handleDlt_withMalformedPayload_doesNotThrow() {
        String malformed = "not-a-json";

        assertDoesNotThrow(() -> listener.handleDlt(malformed, null, "kafka-passthrough"));
    }
}

