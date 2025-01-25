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
public class ProductValidationHandler extends AbstractValidationHandler<Product, RequestValidator> {
    private final ProductService productService;

    protected ProductValidationHandler(ProductService productService) {
        super(Product.class, new RequestValidator());
        this.productService = productService;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.getAllProducts(), Product.class);
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.getProductById(Long.parseLong(request.pathVariable("id"))),
                        Product.class);
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Mono<Void> productMono = Mono.just(Long.valueOf(request.pathVariable("id"))).flatMap(productService::deleteProduct);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productMono, Void.class);
    }

    @Override
    protected Mono<ServerResponse> addProduct(Product validBody, ServerRequest originalRequest) {
        Mono<Product> productMono = Mono.just(validBody)
                .flatMap(productService::addProduct);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(productMono, Product.class);
    }

    @Override
    protected Mono<ServerResponse> updateproduct(Product validBody, ServerRequest originalRequest) {
        Mono<Product> productMono = Mono.just(validBody)
                .flatMap(p -> productService.updateProduct(p, Long.parseLong(originalRequest.pathVariable("id"))));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(productMono, Product.class);
    }


}
