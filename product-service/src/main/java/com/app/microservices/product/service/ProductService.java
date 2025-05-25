package com.app.microservices.product.service;

import com.app.microservices.product.dto.ProductRequest;
import com.app.microservices.product.dto.ProductResponse;
import com.app.microservices.product.model.Product;
import com.app.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully");
        return new ProductResponse(savedProduct.getId(),savedProduct.getName(),savedProduct.getDescription(),savedProduct.getPrice());
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(product->new ProductResponse(product.getId(),product.getName(), product.getDescription(), product.getPrice())).toList();
    }

    public ProductResponse getProductById(ObjectId id){
        Product product =  productRepository.findById(id).orElse(null);

        if(product != null){
            return new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice());
        }
        return  null;
    }

}
