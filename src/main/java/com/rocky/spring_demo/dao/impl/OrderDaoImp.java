package com.rocky.spring_demo.dao.impl;

import com.rocky.spring_demo.dao.OrderDao;
import com.rocky.spring_demo.dto.OrderItems;
import com.rocky.spring_demo.dto.OrderQueryParameter;
import com.rocky.spring_demo.mapper.OrderItemRowMapper;
import com.rocky.spring_demo.mapper.OrderRowMapper;
import com.rocky.spring_demo.module.Order;
import com.rocky.spring_demo.module.OrderItems_module;
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

    @Override
    public Order getOrderByid(Integer orderid) {
        String sql = "select order_id,user_id,total_amount,created_date,last_modified_date from `order`" +
                "where order_id=:orderid";
        Map <String,Object> map = new HashMap<>();
        map.put("orderid",orderid);
        List<Order> list=  namedParameterJdbcTemplate.query(sql,map, new OrderRowMapper());
        if(list.size()>0){
            return list.get(0);
        }
        else
            return null;
    }

    @Override
    public Integer countOrder(OrderQueryParameter orderQueryParameter) {
        String sql = "select count(*) from `order` where 1=1";
        Map<String, Object> map = new HashMap<>();
        sql = addfilter(sql,map,orderQueryParameter);
        Integer countOrder = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(map),Integer.class);
        return countOrder;
    }

    @Override
    public List<Order> getOrders(OrderQueryParameter orderQueryParameter) {
        String sql = "select order_id, user_id, total_amount, created_date, last_modified_date from `order` where 1=1";
        Map<String,Object> map = new HashMap<>();
        sql =  addfilter(sql,map,orderQueryParameter);
        sql += " order by created_date " + orderQueryParameter.getSortmethod();
        sql += " limit :limit offset :offset";
        map.put("limit",orderQueryParameter.getLimit());
        map.put("offset",orderQueryParameter.getOffset());
        List<Order> orderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());
        return orderList;
    }

    @Override
    public List<OrderItems_module> getOrderItemByorderId(Integer orderid) {
        String sql = "select order_item.order_item_id, order_item.order_id, order_item.product_id, order_item.quantity, order_item.amount, product.product_name, product.image_url, product.stock from order_item left join product on order_item.product_id=product.product_id where order_item.order_id=:orderid";
        Map<String,Object> map = new HashMap<>();
        map.put("orderid",orderid);
        List<OrderItems_module> orderItemList =  namedParameterJdbcTemplate.query(sql,map,new OrderItemRowMapper());
        return orderItemList;
    }

    public String addfilter(String sql,Map map,OrderQueryParameter orderQueryParameter)
    {
        if(orderQueryParameter.getUserid()!=null){
            sql += " and user_id=:userid";
            map.put("userid",orderQueryParameter.getUserid());
        }
        return sql;
    }

}
