package com.rocky.spring_demo.dao.impl;

import com.rocky.spring_demo.constant.ProductCategory;
import com.rocky.spring_demo.dao.ProductDao;
import com.rocky.spring_demo.dto.ProductQueryParameter;
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
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countProducts(ProductQueryParameter productQueryParameter) {
        String sql = "select count(*) from product where 1=1";
        Map<String,Object> map = new HashMap<>();
        if(productQueryParameter.getCategory()!=null){
            sql += " AND category = :category";
            map.put("category",productQueryParameter.getCategory().name());
        }
        if(productQueryParameter.getSearch()!=null){
            sql+= " AND product_name like :search";
            map.put("search", "%"+productQueryParameter.getSearch()+"%");
        }
        Integer total = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParameter productQueryParameter) {
        String sql ="select product_id,product_name,category,image_url,price,stock,description,create_date,last_modify_date from product where 1=1";
        Map<String,Object> map = new HashMap<>();
        //query parameter define
        if(productQueryParameter.getCategory()!=null){
            sql += " AND category = :category";
            map.put("category",productQueryParameter.getCategory().name());
        }
        if(productQueryParameter.getSearch()!=null){
            sql+= " AND product_name like :search";
            map.put("search", "%"+productQueryParameter.getSearch()+"%");
        }
        //sort
        sql = sql + " ORDER BY " + productQueryParameter.getOrderBy() + " "+ productQueryParameter.getSort();
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParameter.getLimit());
        map.put("offset",productQueryParameter.getOffset());
        return namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());
    }

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

    @Override
    public void updateProduct(Integer productid, ProductRequest productRequest) {
        String sql = "update product set product_name = :product_name,category=:category,image_url=:image_url,price=:price,stock=:stock,description=:description,last_modify_date=:last_modify_date";
        Map<String,Object>map = new HashMap<>();
        map.put("product_name",productRequest.getProduct_name());
        map.put("category",productRequest.getCategory().name());
        map.put("image_url",productRequest.getImage_url());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        map.put("last_modify_date",new Date());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteProductByid(Integer productid) {
        String sql ="delete from product where product_id=:productid";
        Map<String,Object> map = new HashMap<>();
        map.put("productid",productid);
        namedParameterJdbcTemplate.update(sql,map);
    }
}
