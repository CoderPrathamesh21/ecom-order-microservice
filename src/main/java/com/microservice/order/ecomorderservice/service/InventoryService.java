package com.microservice.order.ecomorderservice.service;

import com.microservice.order.ecomorderservice.client.InventoryClient;
import com.microservice.order.ecomorderservice.dto.Inventory;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class InventoryService {

    private final InventoryClient inventoryClient;

    public InventoryService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    /*@Retryable(retryFor = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    @RateLimiter(name = "inventoryService", fallbackMethod = "fallbackMethod")
    @CircuitBreaker(name = "inventoryServiceCircuitBreaker", fallbackMethod = "circuitBreakerFallbackMethod")*/
    @TimeLimiter(name = "inventoryServiceTimeLimiter", fallbackMethod = "timeLimiterFallbackMethod")
    public CompletableFuture<Inventory> getInventory(Long productId) {
        System.out.println("Calling Inventory Service for productId: " + productId);
        return CompletableFuture.supplyAsync(() -> inventoryClient.getInventory(productId));
    }

    /*public Inventory fallbackMethod(Long productId, Throwable throwable) {
        System.out.println("Fallback method called for productId: " + productId + " due to: " + throwable.getMessage());
        // Return a default Inventory object or handle the fallback logic as needed
        return new Inventory(productId, 0); // Assuming 0 quantity as a fallback
    }*/

    /*public Inventory circuitBreakerFallbackMethod(Long productId, Throwable throwable) {
        System.out.println("Fallback method called for productId: " + productId + " due to: " + throwable.getMessage());
        // Return a default Inventory object or handle the fallback logic as needed
        return new Inventory(productId, 0); // Assuming 0 quantity as a fallback
    }*/

    public CompletableFuture<Inventory> timeLimiterFallbackMethod(Long productId, Throwable throwable) {
        System.out.println("Fallback method called for productId: " + productId + " due to: " + throwable.getMessage());
        // Return a default Inventory object or handle the fallback logic as needed
        return CompletableFuture.completedFuture(new Inventory(productId, 0)); // Assuming 0 quantity as a fallback
    }

}
