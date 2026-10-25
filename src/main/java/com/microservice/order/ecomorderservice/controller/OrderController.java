package com.microservice.order.ecomorderservice.controller;

import com.microservice.order.ecomorderservice.service.AsyncOrderService;
import com.microservice.order.ecomorderservice.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final AsyncOrderService asyncOrderService;

    public OrderController(OrderService orderService, AsyncOrderService asyncOrderService) {
        this.orderService = orderService;
        this.asyncOrderService = asyncOrderService;
    }

    @PostMapping("/{productId}")
    public String placeOrder(@PathVariable Long productId) throws ExecutionException, InterruptedException {
        return orderService.placeOrder(productId);
    }

    @PostMapping("async/{productId}")
    public String placeOrderAsync(@PathVariable Long productId) throws ExecutionException, InterruptedException {
        return asyncOrderService.placeOrder(productId);
    }

}
