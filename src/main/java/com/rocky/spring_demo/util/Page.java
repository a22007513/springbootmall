package com.rocky.spring_demo.util;

import com.rocky.spring_demo.module.Product;
import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    Integer offset;
    Integer limit;
    Integer total;
    List<Product> result;
}
