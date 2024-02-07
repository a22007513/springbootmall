package com.rocky.spring_demo.dao;

import com.rocky.spring_demo.dto.OrderCreateRequest;
import com.rocky.spring_demo.dto.OrderItems;
import com.rocky.spring_demo.dto.OrderQueryParameter;
import com.rocky.spring_demo.module.Order;
import com.rocky.spring_demo.module.OrderItems_module;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userid, Integer total_amaount);

    Order getOrderByid(Integer orderid);

    List<OrderItems_module> getOrderItemByorderId(Integer orderid);

    Integer countOrder(OrderQueryParameter orderQueryParameter);

    List<Order> getOrders(OrderQueryParameter orderQueryParameter);

    void createOrderItem(Integer orderid, List<OrderItems> orderItemsList);
}
