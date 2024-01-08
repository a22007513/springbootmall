package com.rocky.spring_demo.service.impl;

import com.rocky.spring_demo.dao.ProductDao;
import com.rocky.spring_demo.module.Product;
import com.rocky.spring_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductByid(Integer productid) {
        return productDao.getProductByid(productid);
    }
}
