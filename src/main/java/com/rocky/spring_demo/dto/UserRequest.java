package com.rocky.spring_demo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
