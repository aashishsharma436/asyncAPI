package com.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Table(name = "public.product")
public class ProductEntity implements Serializable {
    @Id
    private Long id;
    private String productName;
    private String productType;
    private Double price;
    private Integer quantity;
}
