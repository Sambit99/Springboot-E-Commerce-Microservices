package com.app.microservices.order.controller;

import com.app.microservices.order.dto.OrderRequest;
import com.app.microservices.order.model.Order;
import com.app.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        Order order = orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }

    @GetMapping()
    public void getOrderbyId(){

    }
}
