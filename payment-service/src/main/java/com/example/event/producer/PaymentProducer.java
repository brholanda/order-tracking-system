package com.example.event.producer;

import com.kafka.common.dto.OrderDTO;
import com.kafka.common.dto.OrderStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderDTO orderDTO) {
        String topic = defineTopic(orderDTO);
        kafkaTemplate.send(topic, orderDTO.getOrderId(), orderDTO);
    }

    private static String defineTopic(OrderDTO orderDTO) {
        return orderDTO.getStatus().equals(OrderStatus.PAYMENT_DENIED) ? "payment-denied" : "payment-processed";
    }

}
