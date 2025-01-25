package com.practice.service;

import com.practice.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<Product> getAllProducts();

    Mono<Product> getProductById(Long id);

    Mono<Product> addProduct(Product product);

    Mono<Product> updateProduct(Product product, Long id);

    Mono<Void> deleteProduct(Long id);
}
