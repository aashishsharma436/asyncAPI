package com.practice.controller;

import com.practice.handler.ProductHandler;
import com.practice.model.Product;
import com.practice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v2")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> getProductById(@PathVariable("id") Long id) {
        return service.getProductById(id);
    }

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@Valid @RequestBody Product product) {
        return service.addProduct(product);
    }

    @PutMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return service.updateProduct(product, id);
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<String>> deleteProduct(@PathVariable("id") Long id) {
        return service.deleteProduct(id)
                .map(response -> ResponseEntity.ok().body(response));
    }
}
