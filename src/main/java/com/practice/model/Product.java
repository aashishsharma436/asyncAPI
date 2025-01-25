package com.practice.model;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class Product{
        @NotEmpty(message = "Product Name can not be empty")
        private String productName;

        @NotEmpty(message = "Product type can not be empty")
        private String productType;

        @NotNull(message = "Product price can not be empty")
        @Positive(message = "Price must be greater than zero")
        private Double price;

        @NotNull(message = "Quantity can not be empty")
        @Positive(message = "Quantity must be greater than zero")
        private Integer quantity;
}
