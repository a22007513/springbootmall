package com.rocky.spring_demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocky.spring_demo.constant.ProductCategory;
import com.rocky.spring_demo.dto.ProductQueryParameter;
import com.rocky.spring_demo.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getProductByid_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/1");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.product_id",equalTo(1)))
                .andExpect(jsonPath("$.category",equalTo("FOOD")))
                .andExpect(jsonPath("$.create_date",notNullValue()));
    }

    @Test
    public void getProductByid_noContain() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/100");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void getProducts() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.offset",equalTo(0)))
                .andExpect(jsonPath("$.limit",equalTo(5)))
                .andExpect(jsonPath("$.total",equalTo(7)))
                .andExpect(jsonPath("$.result",hasSize(5)))
                .andExpect(jsonPath("$.result[0].product_name",equalTo("Tesla")))
                .andExpect(jsonPath("$.result[1].product_name",equalTo("Benz")));
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProduct_name("test_product");
        String catagory = "FOOD";
        productRequest.setCategory(ProductCategory.valueOf(catagory));
        productRequest.setDescription("test item");
        productRequest.setImage_url("https://fortesturl");
        productRequest.setPrice(123);
        productRequest.setStock(123);
        Date now = new Date();
        productRequest.setCreate_date(now);
        productRequest.setLast_modify_date(now);
        String json = objectMapper.writeValueAsString(productRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(jsonPath("$.product_name",equalTo("test_product")))
                .andExpect(jsonPath("$.category",equalTo("FOOD")))
                .andExpect(jsonPath("$.description",equalTo("test item")))
                .andExpect(jsonPath("$.image_url",equalTo("https://fortesturl")))
                .andExpect(jsonPath("$.price",equalTo(123)))
                .andExpect(jsonPath("$.stock",equalTo(123)))
                .andExpect(jsonPath("$.create_date",notNullValue()))
                .andExpect(jsonPath("$.last_modify_date",notNullValue()));
    }

    @Test
    public void createProduct_illegalArgument() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        productRequest.setProduct_name("test");
        String json = objectMapper.writeValueAsString(productRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @Transactional
    public void updateproduct_success() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        productRequest.setProduct_name("test_product");
        String catagory = "FOOD";
        productRequest.setCategory(ProductCategory.valueOf(catagory));
        productRequest.setDescription("test item");
        productRequest.setImage_url("https://fortesturl");
        productRequest.setPrice(123);
        productRequest.setStock(123);
        Date now = new Date();
        productRequest.setCreate_date(now);
        productRequest.setLast_modify_date(now);
        String json = objectMapper.writeValueAsString(productRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/{productid}",3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.product_name",equalTo("test_product")))
                .andExpect(jsonPath("$.category",equalTo("FOOD")))
                .andExpect(jsonPath("$.description",equalTo("test item")))
                .andExpect(jsonPath("$.image_url",equalTo("https://fortesturl")))
                .andExpect(jsonPath("$.price",equalTo(123)))
                .andExpect(jsonPath("$.stock",equalTo(123)))
                .andExpect(jsonPath("$.create_date",notNullValue()))
                .andExpect(jsonPath("$.last_modify_date",notNullValue()));
    }

    @Test
    @Transactional
    public void updateproduct_notfoundArgument() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        productRequest.setProduct_name("test_product");
        String catagory = "FOOD";
        productRequest.setCategory(ProductCategory.valueOf(catagory));
        productRequest.setDescription("test item");
        productRequest.setImage_url("https://fortesturl");
        productRequest.setPrice(123);
        productRequest.setStock(123);
        Date now = new Date();
        productRequest.setCreate_date(now);
        productRequest.setLast_modify_date(now);
        String json = objectMapper.writeValueAsString(productRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/{productid}",100)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    @Transactional
    public void updateproduct_illeageArgument() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        productRequest.setProduct_name("test_product");
        String json = objectMapper.writeValueAsString(productRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/{productid}",3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @Transactional
    public void deleteproduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/{productid}",3);
        mockMvc.perform(requestBuilder).andExpect(status().is(204));
    }

    @Test
    public void deleteproduct_illeageArgument() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/{productid}",100);
        mockMvc.perform(requestBuilder).andExpect(status().is(204));
    }

    @Test
    public void getproducts_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products")
                .param("sort","asc")
                .param("orderBy","price")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.result",hasSize(5)))
                .andExpect(jsonPath( "$.result[0].product_name",equalTo("好吃又鮮甜的蘋果橘子")))
                .andExpect(jsonPath( "$.result[1].product_name",equalTo("蘋果（澳洲）")))
                .andExpect(jsonPath("$.result[1].category",equalTo("FOOD")))
                .andExpect(jsonPath("$.result[1].stock",equalTo(10)))
                .andExpect(jsonPath("$.result[1].description",equalTo("這是來自澳洲的蘋果！")));
    }

    @Test
    public void getproducts_pagnation() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products")
                .param("limit","2")
                .param("offset","2")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.result",hasSize(2)))
                .andExpect(jsonPath( "$.result[0].product_name",equalTo("BMW")))
                .andExpect(jsonPath( "$.result[1].product_name",equalTo("Toyota")))
                .andExpect(jsonPath("$.result[1].category",equalTo("CAR")))
                .andExpect(jsonPath("$.result[1].stock",equalTo(5)))
                .andExpect(jsonPath("$.limit",equalTo(2)))
                .andExpect(jsonPath("$.offset",equalTo(2)));
    }

    @Test
    public void getproducts_filtering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products")
                .param("search","B")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.result",hasSize(2)))
                .andExpect(jsonPath( "$.result[0].product_name",equalTo("Benz")))
                .andExpect(jsonPath( "$.result[1].product_name",equalTo("BMW")))
                .andExpect(jsonPath("$.result[1].category",equalTo("CAR")))
                .andExpect(jsonPath("$.result[1].stock",equalTo(3)));
    }
}