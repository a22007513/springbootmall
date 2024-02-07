package com.rocky.spring_demo.module;

import lombok.Data;

@Data
public class OrderItems_module {

    Integer orderItemId;
    Integer orderId;
    Integer productId;
    Integer quantity;
    Integer amount;
    String productName;
    String imageUrl;
    Integer stock;
}
