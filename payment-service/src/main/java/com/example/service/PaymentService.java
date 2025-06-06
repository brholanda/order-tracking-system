package com.example.service;

import com.example.event.producer.PaymentProducer;
import com.kafka.common.dto.OrderDTO;
import com.kafka.common.dto.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private static final BigDecimal PAYMENT_LIMIT = BigDecimal.valueOf(1000);
    @Autowired
    private PaymentProducer producer;

    public void processPayment(OrderDTO orderDTO) {
        orderDTO.setStatus(
                orderDTO.getAmount().compareTo(PAYMENT_LIMIT) < 0
                        ? OrderStatus.PAYMENT_ACCEPTED
                        : OrderStatus.PAYMENT_DENIED
        );
        producer.sendOrder(orderDTO);
    }
}
