package com.microservice.order.ecomorderservice.service;

import com.microservice.order.ecomorderservice.client.InventoryClient;
import com.microservice.order.ecomorderservice.config.RestTemplateConfig;
import com.microservice.order.ecomorderservice.dto.Inventory;
import com.microservice.order.ecomorderservice.exceptions.MyCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private final RestTemplate restTemplate;
    private final RestClient restClient;
    private final InventoryClient inventoryClient;

    public OrderService(RestTemplate restTemplateConfig, RestClient restClient, InventoryClient inventoryClient) {
        this.restTemplate = restTemplateConfig;
        this.restClient = restClient;
        this.inventoryClient = inventoryClient;
    }

    public String placeOrder(Long productId) {

        //RestTemplate
        //String inventoryStatus = restTemplate.getForObject("http://localhost:8067/api/v1/inventory/" + productId, String.class);

        //RestClient
        /*String inventoryStatus = restClient.get()
                .uri("http://localhost:8067/api/v1/inventory/{productId}", productId)
                .retrieve()
                .body(String.class);
        return "IN STOCK".equals(inventoryStatus) ? "Order Placed Successfully" : "Product Out Of Stock";*/

        /*ResponseEntity<Inventory> entity = restClient.get()
                .uri("http://localhost:8067/api/v1/inventory/{productId}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) ->
                {
                    throw new MyCustomException(response.getStatusCode(), response.getHeaders());
                })
                .toEntity(Inventory.class);*/

        //FeignClient
        Inventory inventory = inventoryClient.getInventory(productId);
        Integer quantity = inventory.getQuantity();
        updateInventory(inventory);

        return quantity > 0 ?
                "Order Placed Successfully" : "Product Out Of Stock";
    }

    private void updateInventory(Inventory inventory) {
        inventory.setQuantity(inventory.getQuantity() - 1);
        inventoryClient.updateInventory(inventory);

        //rest client call
        /*restClient.post()
                .uri("http://localhost:8067/api/v1/inventory")
                .body(inventory)
                .retrieve()
                .toBodilessEntity();*/
    }
}
