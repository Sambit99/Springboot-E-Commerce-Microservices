package com.app.microservices.product.controller;

import com.app.microservices.product.dto.ProductRequest;
import com.app.microservices.product.dto.ProductResponse;
import com.app.microservices.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable ObjectId id){
        return productService.getProductById(id);
    }

    @GetMapping()
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
}
