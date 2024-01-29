package com.rocky.spring_demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocky.spring_demo.dao.UserDao;
import com.rocky.spring_demo.dto.UserRequest;
import com.rocky.spring_demo.dto.UserloginRequest;
import com.rocky.spring_demo.module.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserDao userDao;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("45678@gmail.com");
        userRequest.setPassword("213fg");
        String json =  objectMapper.writeValueAsString(userRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.email",equalTo("45678@gmail.com")))
                .andExpect(jsonPath("$.create_date",notNullValue()))
                .andExpect(jsonPath("$.last_modify_date",notNullValue()));

        User user = userDao.getUserByEmail(userRequest.getEmail());
        assertNotEquals(user.getPassword(),userRequest.getPassword());
    }

    @Test
    public void createUser_WorngEmailFormat() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("12");
        userRequest.setPassword("1235");
        String json =  objectMapper.writeValueAsString(userRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void createUser_illegalArgument() throws Exception {
        UserRequest userRequest =new UserRequest();
        userRequest.setEmail("1234@gmail.com");
        String json = objectMapper.writeValueAsString(userRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void createUser_duplicateEmail() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("789@gmail.com");
        userRequest.setPassword("test2");
        String json =  objectMapper.writeValueAsString(userRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andDo(print());
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void login_sucess() throws Exception {
        UserloginRequest userloginRequest = new UserloginRequest();
        userloginRequest.setEmail("789@gmail.com");
        userloginRequest.setPassword("test2");
        String json =  objectMapper.writeValueAsString(userloginRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        register(userloginRequest);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.email",equalTo(userloginRequest.getEmail())))
                .andExpect(jsonPath("$.create_date",notNullValue()))
                .andExpect(jsonPath("$.last_modify_date",notNullValue()));
    }

    @Test
    public void login_unregisterEmail() throws Exception {
        UserloginRequest userloginRequest = new UserloginRequest();
        userloginRequest.setEmail("1234567@gmail.com");
        userloginRequest.setPassword("test456");
        String json =  objectMapper.writeValueAsString(userloginRequest);
        register(userloginRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void login_illegalArgument() throws Exception {
        UserloginRequest userloginRequest = new UserloginRequest();
        userloginRequest.setEmail("0489@hmail.com");
        String json = objectMapper.writeValueAsString(userloginRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        register(userloginRequest);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void login_wrongPassword() throws Exception {
        UserloginRequest userloginRequest = new UserloginRequest();
        userloginRequest.setEmail("789@gmail.com");
        userloginRequest.setPassword("test2");
        String json =  objectMapper.writeValueAsString(userloginRequest);
        register(userloginRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    public void register(UserloginRequest userloginRequest) throws Exception {

        String json =  objectMapper.writeValueAsString(userloginRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder);
    }
}