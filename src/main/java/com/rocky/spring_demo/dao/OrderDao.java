package com.rocky.spring_demo.dao;

import com.rocky.spring_demo.dto.OrderCreateRequest;
import com.rocky.spring_demo.dto.OrderItems;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userid, Integer total_amaount);

    void createOrderItem(Integer orderid, List<OrderItems> orderItemsList);
}
