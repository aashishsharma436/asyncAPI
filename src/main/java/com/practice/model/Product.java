package com.practice.model;

import jakarta.validation.constraints.*;

public record Product(
        Long id,
        @NotEmpty(message = "please enter the product name") String productName,
        @NotEmpty(message = "please enter the product type") String productType,

        @NotNull(message = "please enter the Price")
        @Digits(integer = 10,fraction = 2, message = "Price must have up to 6 digits and 2 decimal places")
        Double price,

        @NotNull(message = "please enter the product Quantity")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity) {
}
