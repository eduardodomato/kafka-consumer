package com.messaging.kafka.dto;

import lombok.Data;

@Data
public class Customer {
    private String id;
    private String name;
    private String email;
    private String phone;
}
