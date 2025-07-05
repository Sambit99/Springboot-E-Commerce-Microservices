package com.app.microservices.notification.service;

import com.app.microservices.notification.order.event.OrderPlacedEvent;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final KafkaTemplate kafkaTemplate;

    private final EmailService emailService;

    @KafkaListener(topics = "order-placed")
    public void listenTopics(OrderPlacedEvent orderPlacedEvent){
        log.info("Got message form order-placed topic {}", orderPlacedEvent);
        emailService.sendEmail("example@xyz.com",
                String.format("Your Order with OrderNumber %s is placed successfully",orderPlacedEvent.getOrderNumber()),
                String.format("""
                        Hi,
                        
                        Your Order with OrderNumber %s is now placed successfully.
                        
                        Best Regards,
                        Spring 
                        """, orderPlacedEvent.getOrderNumber()));
    }
}
