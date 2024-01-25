package com.rocky.spring_demo.service.impl;

import com.rocky.spring_demo.dao.UserDao;
import com.rocky.spring_demo.dto.UserRequest;
import com.rocky.spring_demo.module.User;
import com.rocky.spring_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImp implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User getUserByid(Integer id) {
        return userDao.getUserBtid(id);
    }

    @Override
    public Integer register(UserRequest userRequest) {
        return userDao.createUser(userRequest);
    }
}
