package com.rocky.spring_demo.module;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer userid;
    private String email;
    private String password;
    private Date create_date;
    private Date last_modify_date;
}
