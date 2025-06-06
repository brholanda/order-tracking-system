package com.example.event.consumer;

import com.example.service.PaymentService;
import com.kafka.common.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PaymentConsumer.class);

    @Autowired
    private PaymentService service;

    @KafkaListener(topics = "new-orders", groupId = "payment-group")
    public void consume(OrderDTO orderDTO) {
        logger.info("Order received: {}", orderDTO.getOrderId());
        service.processPayment(orderDTO);
    }
}
