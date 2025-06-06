package com.example.event.producer;

import com.kafka.common.dto.OrderDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderDTO orderDTO) {
        kafkaTemplate.send("payment-processed", orderDTO.getOrderId(), orderDTO);
    }

}
