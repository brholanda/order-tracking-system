package com.kafka.common.dto;

public enum OrderStatus {
    ORDER_CREATED,
    PAYMENT_ACCEPTED,
    PAYMENT_DENIED,
    INVENTORY_RESERVED,
    INVENTORY_FAILED,
    ORDER_COMPLETED,
    ORDER_CANCELLED
}
