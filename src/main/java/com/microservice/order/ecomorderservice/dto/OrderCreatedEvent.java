package com.microservice.order.ecomorderservice.dto;

public record OrderCreatedEvent(
        Long orderId,
        Long customerId,
        Long productId,
        int quantity
)
{}
