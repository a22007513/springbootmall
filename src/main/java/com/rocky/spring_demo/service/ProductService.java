package com.rocky.spring_demo.service;

import com.rocky.spring_demo.dao.ProductDao;
import com.rocky.spring_demo.dto.ProductRequest;
import com.rocky.spring_demo.module.Product;
import org.springframework.stereotype.Component;

public interface ProductService {
    Product getProductByid(Integer productid);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productid,ProductRequest productRequest);

    void deleteProductByid(Integer productid);
}
