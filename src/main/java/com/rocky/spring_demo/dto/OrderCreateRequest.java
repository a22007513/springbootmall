package com.rocky.spring_demo.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderCreateRequest {

    List<buyItems> orderlist;
}
