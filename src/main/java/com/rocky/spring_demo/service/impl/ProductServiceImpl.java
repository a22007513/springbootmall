package com.rocky.spring_demo.service.impl;

import com.rocky.spring_demo.constant.ProductCategory;
import com.rocky.spring_demo.dao.ProductDao;
import com.rocky.spring_demo.dto.ProductQueryParameter;
import com.rocky.spring_demo.dto.ProductRequest;
import com.rocky.spring_demo.module.Product;
import com.rocky.spring_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer countProducts(ProductQueryParameter productQueryParameter) {
        return productDao.countProducts(productQueryParameter);
    }

    @Override
    public List<Product> getProducts(ProductQueryParameter productQueryParameter) {
        return productDao.getProducts(productQueryParameter);
    }

    @Override
    public Product getProductByid(Integer productid) {
        return productDao.getProductByid(productid);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productid, ProductRequest productRequest) {
        productDao.updateProduct(productid,productRequest);
    }

    @Override
    public void deleteProductByid(Integer productid) {
        productDao.deleteProductByid(productid);
    }
}
