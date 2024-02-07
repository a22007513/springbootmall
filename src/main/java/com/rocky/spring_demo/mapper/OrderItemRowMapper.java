package com.rocky.spring_demo.mapper;


import com.rocky.spring_demo.module.OrderItems_module;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItems_module> {

    @Override
    public OrderItems_module mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItems_module orderItems_module = new OrderItems_module();
        //original order item table column convert
        orderItems_module.setOrderItemId(resultSet.getInt("order_item_id"));
        orderItems_module.setOrderId(resultSet.getInt("order_id"));
        orderItems_module.setProductId(resultSet.getInt("product_id"));
        orderItems_module.setQuantity(resultSet.getInt("quantity"));
        orderItems_module.setAmount(resultSet.getInt("amount"));
        //join product table column with order item
        orderItems_module.setProductName(resultSet.getString("product_name"));
        orderItems_module.setImageUrl(resultSet.getString("image_url"));
        orderItems_module.setStock(resultSet.getInt("stock"));
        return orderItems_module;
    }
}
