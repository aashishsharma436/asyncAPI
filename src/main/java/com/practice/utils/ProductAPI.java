package com.practice.utils;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

public class ProductAPI {
    public static final RequestPredicate ACCEPT_JSON = accept(MediaType.APPLICATION_JSON);
    public static final String BASE_URL = "/api/v1/product";
    public static final String GET_PRODUCTS = BASE_URL;
    public static final String GET_PRODUCT_BY_ID = BASE_URL.concat("/{id}");
    public static final String ADD_PRODUCT = BASE_URL;
    public static final String UPDATE_PRODUCT = BASE_URL.concat("/{id}");
    public static final String DELETE_PRODUCT = BASE_URL.concat("/{id}");
}
