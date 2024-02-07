package com.rocky.spring_demo.module;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {

    Integer orderid;
    Integer userid;
    Integer totalAmount;
    Date createDate;
    Date lastModifyDate;
    List<OrderItems_module> orderItemsList;

}
