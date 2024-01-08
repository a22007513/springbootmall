package com.rocky.spring_demo.module;
import com.rocky.spring_demo.constant.ProductCategory;
import lombok.Data;

import java.util.Date;

@Data
public class Product {
    int product_id;
    String product_name;
    ProductCategory category;
    String image_url;
    int price;
    int stock;
    String description;
    Date create_date;
    Date last_modify_date;
}
