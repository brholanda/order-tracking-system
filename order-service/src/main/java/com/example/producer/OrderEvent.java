package com.example.producer;

import com.example.model.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderEvent {

    private String orderId;
    private String status;
    private BigDecimal amount;
    private List<Product> products;
}
