package com.rocky.spring_demo.dao.impl;

import com.rocky.spring_demo.dao.OrderDao;
import com.rocky.spring_demo.dto.OrderCreateRequest;
import com.rocky.spring_demo.dto.OrderItems;
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
public class OrderDaoImp implements OrderDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userid, Integer total_amount) {
        String sql = "INSERT INTO `order` (user_id,total_amount,created_date,last_modified_date) VALUES " +
                "(:userid,:total_amount,:created_date,:last_modified_date)";
        Map<String,Object> map = new HashMap<>();
        map.put("userid",userid);
        map.put("total_amount",total_amount);
        Date now = new Date();
        map.put("created_date",now);
        map.put("last_modified_date",now);
        KeyHolder keyHolder =  new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map),keyHolder);
        int orderid = keyHolder.getKey().intValue();
        return orderid;
    }

    @Override
    public void createOrderItem(Integer orderid, List<OrderItems> orderItemsList) {
        String sql = "INSERT INTO order_item (order_id,product_id, quantity, amount) VALUES " +
                "(:order_id,:product_id,:quantity,:amount)";
        int n = orderItemsList.size();
        MapSqlParameterSource [] mapSqlParameterSources = new MapSqlParameterSource[n];
        for(int i =0; i<n; i++){
            OrderItems orderItems = orderItemsList.get(i);
            mapSqlParameterSources[i] = new MapSqlParameterSource();
            mapSqlParameterSources[i].addValue("order_id",orderid);
            mapSqlParameterSources[i].addValue("product_id",orderItems.getProdict_id());
            mapSqlParameterSources[i].addValue("quantity",orderItems.getQuanity());
            mapSqlParameterSources[i].addValue("amount",orderItems.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql,mapSqlParameterSources);

    }
}
