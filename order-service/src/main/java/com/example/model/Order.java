package com.example.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

import static com.kafka.common.dto.OrderStatus.ORDER_CREATED;

@Data
public class Order {

    @Id
    private String id;
    @NotNull(message = "Order request must indicate amount value")
    private BigDecimal amount;
    private String status = ORDER_CREATED.toString();
    @NotNull(message = "Order request must contain products")
    @NotEmpty(message = "Order request products list should not be empty")
    private @Valid List<Product> products;
}
