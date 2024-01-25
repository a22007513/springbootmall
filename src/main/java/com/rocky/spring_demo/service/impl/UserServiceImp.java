package com.rocky.spring_demo.service.impl;

import com.rocky.spring_demo.dao.UserDao;
import com.rocky.spring_demo.dto.UserRequest;
import com.rocky.spring_demo.dto.UserloginRequest;
import com.rocky.spring_demo.module.User;
import com.rocky.spring_demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImp implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    UserDao userDao;

    @Override
    public User getUserByid(Integer id) {
        return userDao.getUserBtid(id);
    }

    @Override
    public Integer register(UserRequest userRequest) {
        User user =  userDao.getUserByEmail(userRequest.getEmail());
        if(user!=null){
            logger.warn("email {} already been registered",userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else{
            return userDao.createUser(userRequest);
        }
    }

    @Override
    public User login(UserloginRequest userloginRequest) {
        User user = userDao.getUserByEmail(userloginRequest.getEmail());
        if(user==null){
            logger.warn("This email {} is not register",userloginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword().equals(userloginRequest.getPassword())){
            return  user;
        }
        else {
            logger.warn("This email {} login with wrong password",userloginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
