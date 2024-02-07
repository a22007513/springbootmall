package com.rocky.spring_demo.util;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    Integer offset;
    Integer limit;
    Integer total;
    List<?> result;
}
