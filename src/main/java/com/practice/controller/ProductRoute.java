package com.practice.controller;

import com.practice.constant.ProductAPI;
import com.practice.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class ProductRoute {

/*
    It is alternative approach to use router rather than controller
    # Improves Performance:
      -> Functional routing uses fewer resources compared to @RestController (which depends on reflection and proxies).
      -> More efficient in high-throughput applications (e.g., microservices).
    # More Reactive: Built natively for Spring WebFlux and non-blocking programming.
    # More Flexible:
      -> Easily compose and modify routes.
      -> You can dynamically modify handlers and routes at runtime.
*/

    @Bean
    public RouterFunction<ServerResponse> routerFunction(ProductHandler productHandler) {
        return RouterFunctions
                .route(GET(ProductAPI.GET_PRODUCTS).and(ProductAPI.ACCEPT_JSON), productHandler::getAllProducts)
                .andRoute(GET(ProductAPI.GET_PRODUCT_BY_ID).and(ProductAPI.ACCEPT_JSON), productHandler::getProductById)
                .andRoute(POST(ProductAPI.ADD_PRODUCT).and(ProductAPI.ACCEPT_JSON), productHandler::handleRequest)
                .andRoute(DELETE(ProductAPI.DELETE_PRODUCT).and(ProductAPI.ACCEPT_JSON), productHandler::deleteProduct)
                .andRoute(PUT(ProductAPI.UPDATE_PRODUCT).and(ProductAPI.ACCEPT_JSON), productHandler::handleRequest);
    }
}
