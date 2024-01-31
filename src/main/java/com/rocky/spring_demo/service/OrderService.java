package com.rocky.spring_demo.service;

import com.rocky.spring_demo.dto.OrderCreateRequest;

public interface OrderService {

    Integer createOrder(Integer userid, OrderCreateRequest orderCreateRequest);
}
