package com.rocky.spring_demo.dao.impl;

import com.rocky.spring_demo.dao.UserDao;
import com.rocky.spring_demo.dto.UserRequest;
import com.rocky.spring_demo.mapper.UserRowMapper;
import com.rocky.spring_demo.module.User;
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
public class UserDaoImp implements UserDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRequest userRequest) {
        String sql = "INSERT INTO user (email,password,create_date,last_modify_date) VALUES" +
                "(:email,:password,:create_date,:last_modify_date)";
        Map<String,Object> map = new HashMap<>();
        map.put("email",userRequest.getEmail());
        map.put("password",userRequest.getPassword());
        Date now = new Date();
        map.put("create_date",now);
        map.put("last_modify_date",now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer id = keyHolder.getKey().intValue();
        return id;
    }

    @Override
    public User getUserBtid(Integer userid) {
        String sql = "select user_id,email,password,create_date,last_modify_date from user where user_id=:userid";
        Map<String,Object> map = new HashMap<>();
        map.put("userid",userid);
        List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        if(userList.size()>0){
            return userList.get(0);
        }
        else {
            return null;
        }
    }
}
