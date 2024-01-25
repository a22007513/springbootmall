package com.rocky.spring_demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class UserloginRequest {

    @NotBlank
    String email;

    @NotBlank
    String password;
}
