package com.app.microservices.order.service;

import com.app.microservices.order.dto.OrderRequest;
import com.app.microservices.order.model.Order;
import com.app.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public Order placeOrder(OrderRequest orderRequest){
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
