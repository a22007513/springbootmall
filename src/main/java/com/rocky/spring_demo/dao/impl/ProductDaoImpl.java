package com.rocky.spring_demo.dao.impl;

import com.rocky.spring_demo.dao.ProductDao;
import com.rocky.spring_demo.dto.ProductRequest;
import com.rocky.spring_demo.mapper.ProductRowMapper;
import com.rocky.spring_demo.module.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductByid(Integer productId) {
        String sql = "select product_id,product_name,category,image_url,price,stock,description,create_date,last_modify_date from product where product_id = :productId";
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

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "insert into product (product_name,category,image_url,price,stock,description,create_date,last_modify_date) values (:product_name,:category,:image_url,:price,:stock,:description,:create_date,:last_modify_date)";
        Map<String,Object> map = new HashMap<>();
        map.put("product_name",productRequest.getProduct_name());
        //System.out.println(productRequest.getCategory());
        map.put("category",productRequest.getCategory().name());
        map.put("image_url",productRequest.getImage_url());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        Date now = new Date();
        map.put("create_date",now);
        map.put("last_modify_date",now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        Integer productid =keyHolder.getKey().intValue();
        return  productid;
    }
}
