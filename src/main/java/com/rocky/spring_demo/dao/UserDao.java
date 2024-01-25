package com.rocky.spring_demo.dao;

import com.rocky.spring_demo.dto.UserRequest;
import com.rocky.spring_demo.module.User;

public interface UserDao {

    Integer createUser(UserRequest userRequest);

    User getUserByEmail(String email);

    User getUserBtid(Integer userid);
}
