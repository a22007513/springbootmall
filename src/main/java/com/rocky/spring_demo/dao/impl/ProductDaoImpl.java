package com.rocky.spring_demo.dao.impl;

import com.rocky.spring_demo.dao.ProductDao;
import com.rocky.spring_demo.mapper.ProductRowMapper;
import com.rocky.spring_demo.module.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductByid(Integer productId) {
        String sql = "select product_name,category,image_url,price,stock,description,create_date,last_modify_date from product where product_id = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql,map, new ProductRowMapper());
        if(productList.size()>0){
            return productList.get(0);
        }
        else{
            return null;
        }
    }
}
