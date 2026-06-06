package com.microservice.order.ecomorderservice.client;

import com.microservice.order.ecomorderservice.client.config.InventoryFeignClientConfig;
import com.microservice.order.ecomorderservice.dto.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventory-service", url = "http://localhost:8067",
        configuration = InventoryFeignClientConfig.class)
public interface InventoryClient {

    @GetMapping("/api/v1/inventory/{productId}")
    Inventory getInventory(@PathVariable Long productId);

    @PostMapping("/api/v1/inventory")
    String updateInventory(Inventory inventory);
}
