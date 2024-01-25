package com.rocky.spring_demo.mapper;

import com.rocky.spring_demo.module.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserid(resultSet.getInt("user_id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setCreate_date(resultSet.getTimestamp("create_date"));
        user.setLast_modify_date(resultSet.getTimestamp("last_modify_date"));
        return user;
    }
}
