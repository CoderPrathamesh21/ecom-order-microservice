package com.microservice.order.ecomorderservice.service;

import com.microservice.order.ecomorderservice.dto.OrderCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class AsyncOrderService {

    private final RabbitTemplate rabbitTemplate;

    public AsyncOrderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String placeOrder(@PathVariable Long productId) {
        // Simulate order processing logic
        OrderCreatedEvent event = new OrderCreatedEvent(1L, 1L, productId, 1);
        rabbitTemplate.convertAndSend("orders.exchange",
                "order.created",
                event);
        return "Order placed successfully for product ID: " + productId;
    }
}
