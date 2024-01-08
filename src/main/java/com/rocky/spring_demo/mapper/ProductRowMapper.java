package com.rocky.spring_demo.mapper;

import com.rocky.spring_demo.constant.ProductCategory;
import com.rocky.spring_demo.module.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setProduct_name(resultSet.getString("product_name"));
        String categorystr = resultSet.getString("category");
        ProductCategory productCategory =  ProductCategory.valueOf(categorystr);
        product.setCategory(productCategory);
        product.setImage_url(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStock(resultSet.getInt("stock"));
        product.setDescription(resultSet.getString("description"));
        product.setCreate_date(resultSet.getDate("create_date"));
        product.setLast_modify_date(resultSet.getDate("last_modify_date"));
        return product;
    }
}
