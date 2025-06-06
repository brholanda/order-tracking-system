package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

import static com.kafka.common.dto.OrderStatus.ORDER_CREATED;

@Data
public class Order {

    @Id
    private String id;
    private BigDecimal amount;
    private String status = ORDER_CREATED.toString();
    private List<Product> products;
}
