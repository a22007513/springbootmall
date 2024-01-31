package com.rocky.spring_demo.controller;

import com.rocky.spring_demo.dto.OrderCreateRequest;
import com.rocky.spring_demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/user/{userid}/order")
public ResponseEntity<Integer> createOrder(@PathVariable Integer userid,
                                           @RequestBody OrderCreateRequest orderCreateRequest){
        orderService.createOrder(userid,orderCreateRequest);
        return ResponseEntity.status(201).body(userid);
    }
}
