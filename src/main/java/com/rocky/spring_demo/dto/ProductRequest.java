package com.rocky.spring_demo.dto;

import com.rocky.spring_demo.constant.ProductCategory;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ProductRequest {

    @NotNull
    String product_name;
    @NotNull
    ProductCategory category;
    @NotNull
    String image_url;
    @NotNull
    int price;
    @NotNull
    int stock;
    String description;
    Date create_date;
    Date last_modify_date;
}
