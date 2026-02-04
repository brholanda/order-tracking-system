package com.example.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Product {

    @NotNull(message = "Product id should be specified")
    private String id;
    @NotNull(message = "Product quantity should be specified")
    private Integer quantity;
}
