package com.rocky.spring_demo.service;

import com.rocky.spring_demo.dto.OrderCreateRequest;
import com.rocky.spring_demo.dto.OrderQueryParameter;
import com.rocky.spring_demo.module.Order;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userid, OrderCreateRequest orderCreateRequest);

    Integer countOrder(OrderQueryParameter orderQueryParameter);

    List<Order> getOrders(OrderQueryParameter orderQueryParameter);

    Order getOrderByid(Integer userid);
}
