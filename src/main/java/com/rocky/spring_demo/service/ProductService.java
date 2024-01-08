package com.rocky.spring_demo.service;

import com.rocky.spring_demo.dao.ProductDao;
import com.rocky.spring_demo.module.Product;
import org.springframework.stereotype.Component;

public interface ProductService {
    Product getProductByid(Integer productid);
}
