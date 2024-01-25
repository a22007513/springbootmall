package com.rocky.spring_demo.controller;

import com.rocky.spring_demo.dto.UserRequest;
import com.rocky.spring_demo.module.User;
import com.rocky.spring_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRequest userRequest){
        Integer userid =  userService.register(userRequest);
        User user = userService.getUserByid(userid);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
