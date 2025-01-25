package com.practice.controller;

import com.practice.handler.ProductValidationHandler;
import com.practice.utils.ProductAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

@Configuration(proxyBeanMethods = false)
public class ProductRoute {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(ProductValidationHandler handler){
        return RouterFunctions
                .route(GET(ProductAPI.GET_PRODUCTS).and(ProductAPI.ACCEPT_JSON), handler::getAllProducts)
                .andRoute(GET(ProductAPI.GET_PRODUCT_BY_ID).and(ProductAPI.ACCEPT_JSON), handler::getProductById)
                .andRoute(POST(ProductAPI.ADD_PRODUCT).and(ProductAPI.ACCEPT_JSON), handler::handleRequest)
                .andRoute(DELETE(ProductAPI.DELETE_PRODUCT).and(ProductAPI.ACCEPT_JSON), handler::deleteProduct)
                .andRoute(PUT(ProductAPI.UPDATE_PRODUCT).and(ProductAPI.ACCEPT_JSON), handler::handleRequest);
    }
}
