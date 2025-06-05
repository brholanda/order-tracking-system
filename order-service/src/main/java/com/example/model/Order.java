package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Order {

    @Id
    private String id;
    private BigDecimal amount;
    private String status = "NEW";
    private List<Product> products;
}
