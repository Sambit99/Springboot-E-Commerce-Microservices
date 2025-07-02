package com.app.microservices.order.service;

import com.app.microservices.order.client.InventoryClient;
import com.app.microservices.order.dto.OrderRequest;
import com.app.microservices.order.event.OrderPlacedEvent;
import com.app.microservices.order.model.Order;
import com.app.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.KafkaClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

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

        // Send message to Kafka Topic
        // OrderNumber, E-Mail

        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(),"abc@example.com");
        log.info("Start - Sending OrderPlaceEvent to Kafka topic order-placed",orderPlacedEvent);
        kafkaTemplate.send("order_placed",orderPlacedEvent);
        log.info("End - Sending OrderPlaceEvent to Kafka topic order-placed",orderPlacedEvent);
        return savedOrder;
    }
}
