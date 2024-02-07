package com.rocky.spring_demo.controller;

import com.rocky.spring_demo.dto.OrderCreateRequest;
import com.rocky.spring_demo.dto.OrderQueryParameter;
import com.rocky.spring_demo.module.Order;
import com.rocky.spring_demo.service.OrderService;
import com.rocky.spring_demo.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/user/{userid}/order")
public ResponseEntity<Order> createOrder(@PathVariable Integer userid,
                                         @RequestBody OrderCreateRequest orderCreateRequest){
        Integer orderid =  orderService.createOrder(userid,orderCreateRequest);
        Order order =  orderService.getOrderByid(orderid);
        return ResponseEntity.status(201).body(order);
    }

    @GetMapping("/user/{userid}/orders")
    public Page<Order> getOrders(@PathVariable Integer userid,
                                 @RequestParam (defaultValue = "0") @Max(100) Integer offset,
                                 @RequestParam (defaultValue = "10") @Max(100) @Min(0) Integer limit,
                                 @RequestParam (defaultValue = "desc") String sortMethod){
        //set query parameter
        OrderQueryParameter orderQueryParameter = new OrderQueryParameter();
        orderQueryParameter.setLimit(limit);
        orderQueryParameter.setOffset(offset);
        orderQueryParameter.setSortmethod(sortMethod);
        orderQueryParameter.setUserid(userid);
        Integer count = orderService.countOrder(orderQueryParameter);
        //set page
        Page<Order> orderPage = new Page<>();
        orderPage.setLimit(limit);
        orderPage.setOffset(limit);
        orderPage.setTotal(count);
        List<Order> orderList = orderService.getOrders(orderQueryParameter);
        orderPage.setResult(orderList);
        return orderPage;
    }
}
