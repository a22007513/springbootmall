package com.rocky.spring_demo.service;

import com.rocky.spring_demo.dto.UserRequest;
import com.rocky.spring_demo.dto.UserloginRequest;
import com.rocky.spring_demo.module.User;
import org.springframework.stereotype.Component;


public interface UserService {

    User getUserByid(Integer id);

    User login(UserloginRequest userloginRequest);
    Integer register(UserRequest userRequest);

}
