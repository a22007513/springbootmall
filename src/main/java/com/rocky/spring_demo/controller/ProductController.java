package com.rocky.spring_demo.controller;

import com.rocky.spring_demo.constant.ProductCategory;
import com.rocky.spring_demo.dto.ProductRequest;
import com.rocky.spring_demo.module.Product;
import com.rocky.spring_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation.ANONYMOUS.required;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam (required = false)ProductCategory category,
    @RequestParam(required = false) String search){
        List<Product> productList  =  productService.getProducts(category,search);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{productid}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productid){
        Product product =  productService.getProductByid(productid);
        if(product!=null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productid = productService.createProduct(productRequest);
        System.out.println(productid);
        Product product =  productService.getProductByid(productid);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productid}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productid ,
                                                 @RequestBody @Valid ProductRequest productRequest){
        Product product =  productService.getProductByid(productid);
        if(product==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProduct(productid,productRequest);
        Product UpdateProduct = productService.getProductByid(productid);
        return ResponseEntity.status(HttpStatus.OK).body(UpdateProduct);
    }

    @DeleteMapping("/products/{productid}")
    public ResponseEntity<?>deleteProduct(@PathVariable Integer productid){
        productService.deleteProductByid(productid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
