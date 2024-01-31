package com.rocky.spring_demo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class buyItems {

    @NotNull
    Integer quantity;

    @NotNull
    Integer productid;
}
