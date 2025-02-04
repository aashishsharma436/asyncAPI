package com.practice.handler;

import com.practice.model.Product;
import com.practice.service.ProductService;
import com.practice.validator.AbstractValidationHandler;
import com.practice.validator.RequestValidator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler extends AbstractValidationHandler<Product, RequestValidator> {
    private final ProductService service;

    public ProductHandler(ProductService service){
        super(Product.class,new RequestValidator());
        this.service = service;
    }

    @Override
    protected Mono<ServerResponse> addProduct(Product validBody, ServerRequest originalRequest) {
        Mono<Product> productMono = Mono.just(validBody).flatMap(service::addProduct);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productMono, Product.class);
    }

    @Override
    protected Mono<ServerResponse> updateProduct(Product validBody, ServerRequest originalRequest) {
        Mono<Product> productMono = Mono.just(validBody)
                .flatMap(p -> service.updateProduct(p, Long.parseLong(originalRequest.pathVariable("id"))));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productMono, Product.class);
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getAllProducts(), Product.class);
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getProductById(Long.parseLong(request.pathVariable("id"))), Product.class);
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Mono<String> productMono = Mono.just(Long.valueOf(request.pathVariable("id")))
                .flatMap(service::deleteProduct);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(productMono, Void.class);
    }

}
