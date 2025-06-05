package com.example.service.mapper;

import com.example.model.Order;
import com.example.producer.OrderEvent;

public abstract class OrderEventMapper {

    public static OrderEvent map(Order order) {
        return OrderEvent.builder()
                .orderId(order.getId())
                .amount(order.getAmount())
                .status(order.getStatus())
                .products(order.getProducts())
                .build();
    }
}
