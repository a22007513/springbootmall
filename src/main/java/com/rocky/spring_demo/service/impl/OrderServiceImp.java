package com.rocky.spring_demo.service.impl;

import com.rocky.spring_demo.dao.OrderDao;
import com.rocky.spring_demo.dao.ProductDao;
import com.rocky.spring_demo.dto.OrderCreateRequest;
import com.rocky.spring_demo.dto.OrderItems;
import com.rocky.spring_demo.dto.buyItems;
import com.rocky.spring_demo.module.Product;
import com.rocky.spring_demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class OrderServiceImp implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductDao productDao;

    @Override
    @Transactional
    public Integer createOrder(Integer userid, OrderCreateRequest orderCreateRequest) {
        List<buyItems> buyItemsList = orderCreateRequest.getOrderlist();
        int n = buyItemsList.size();
        List<OrderItems> orderItemsList = new ArrayList<>();
        int total_amount =0;
        for(int i =0; i<n; i++){
            //input order table info
            buyItems buyItems = buyItemsList.get(i);
            OrderItems orderItems = new OrderItems();
            Product product = productDao.getProductByid(buyItems.getProductid());
            int amount= product.getPrice();
            total_amount += amount * buyItems.getQuantity();

            //convert buyItems to OrderItems
            orderItems.setProdict_id(buyItems.getProductid());
            orderItems.setQuanity(buyItems.getQuantity());
            orderItems.setAmount(amount);
            orderItemsList.add(orderItems);
        }
        Integer orderId =  orderDao.createOrder(userid,total_amount);

        orderDao.createOrderItem(orderId,orderItemsList);

        return orderId;
    }
}
