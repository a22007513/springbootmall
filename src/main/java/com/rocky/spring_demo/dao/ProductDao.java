package com.rocky.spring_demo.dao;

import com.rocky.spring_demo.module.Product;
import org.springframework.stereotype.Component;

public interface ProductDao {

     Product getProductByid(Integer productid);
}