package com.app.microservices.order.service;

import com.app.microservices.order.client.InventoryClient;
import com.app.microservices.order.dto.OrderRequest;
import com.app.microservices.order.model.Order;
import com.app.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public Order placeOrder(OrderRequest orderRequest){

        boolean isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());

        if(!isProductInStock)
            throw new RuntimeException("Product with SkuCode "+orderRequest.skuCode()+" is out of stock");

        // Map OrderRequest to Order
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());

        // Save Order to OrderRepository
        Order savedOrder = orderRepository.save(order);

        return savedOrder;
    }
}
