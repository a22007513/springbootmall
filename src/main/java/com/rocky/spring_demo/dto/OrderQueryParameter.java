package com.rocky.spring_demo.dto;

import lombok.Data;

@Data
public class OrderQueryParameter {

    Integer userid;
    Integer offset;
    Integer limit;
    String sortmethod;
}
