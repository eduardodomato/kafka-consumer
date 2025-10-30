package com.messaging.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.messaging.kafka.dto.Customer;

@Service
@Slf4j
public class MessageListener {

    @KafkaListener(topics = "kafka-passthrough", groupId = "group-1")
    public void consume(String message){
        log.info("Consumer has pulled the message: {}", message);
    }

    @KafkaListener(topics = "kafka-passthrough", groupId = "group-1")
    public void consume(Customer customer){
        log.info("Consumer has pulled the object: {}", customer);
    }
}
