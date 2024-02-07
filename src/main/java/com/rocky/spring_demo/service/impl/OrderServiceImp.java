package com.rocky.spring_demo.service.impl;

import com.rocky.spring_demo.dao.OrderDao;
import com.rocky.spring_demo.dao.ProductDao;
import com.rocky.spring_demo.dao.UserDao;
import com.rocky.spring_demo.dto.OrderCreateRequest;
import com.rocky.spring_demo.dto.OrderItems;
import com.rocky.spring_demo.dto.OrderQueryParameter;
import com.rocky.spring_demo.dto.buyItems;
import com.rocky.spring_demo.module.Order;
import com.rocky.spring_demo.module.OrderItems_module;
import com.rocky.spring_demo.module.Product;
import com.rocky.spring_demo.module.User;
import com.rocky.spring_demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class OrderServiceImp implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImp.class);
    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    UserDao userDao;

    @Override
    @Transactional
    public Integer createOrder(Integer userid, OrderCreateRequest orderCreateRequest) {
        User user = userDao.getUserBtid(userid);
        if(user==null){
            logger.warn("This userid {} is not register",userid);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<buyItems> buyItemsList = orderCreateRequest.getOrderlist();
        int n = buyItemsList.size();
        List<OrderItems> orderItemsList = new ArrayList<>();
        int total_amount =0;
        for(int i =0; i<n; i++){
            //input order table info
            buyItems buyItems = buyItemsList.get(i);
            OrderItems orderItems = new OrderItems();
            Product product = productDao.getProductByid(buyItems.getProductid());

            //verify product stock before create order
            if (product==null){
                logger.warn("product id {} is not exist",buyItems.getProductid());

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else if(product.getStock()< buyItems.getQuantity()){
                logger.warn("{} stock are {}  less then your order {}",product.getProduct_name(),product.getStock(),buyItems.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            productDao.updateProductStockByid(product.getProduct_id(),product.getStock()-buyItems.getQuantity());
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

    @Override
    public Integer countOrder(OrderQueryParameter orderQueryParameter) {
        Integer orderCount = orderDao.countOrder(orderQueryParameter);
        return orderCount;
    }

    @Override
    public List<Order> getOrders(OrderQueryParameter orderQueryParameter) {
       List<Order> orderList = orderDao.getOrders(orderQueryParameter);
       int n = orderList.size();
       for(int i =0; i<n ; i++){
           Order order = orderList.get(i);
           List<OrderItems_module> orderItemList = orderDao.getOrderItemByorderId(order.getOrderid());
           orderList.get(i).setOrderItemsList(orderItemList);
       }
       return  orderList;
    }

    @Override
    public Order getOrderByid(Integer orderid) {
        Order order =  orderDao.getOrderByid(orderid);
        List<OrderItems_module> orderItemList =  orderDao.getOrderItemByorderId(orderid);
        order.setOrderItemsList(orderItemList);
        return order;
    }
}
