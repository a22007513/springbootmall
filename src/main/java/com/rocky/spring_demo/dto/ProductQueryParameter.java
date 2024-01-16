package com.rocky.spring_demo.dto;

import com.rocky.spring_demo.constant.ProductCategory;
import lombok.Data;

@Data
public class ProductQueryParameter {
    String search;
    ProductCategory category;
}
