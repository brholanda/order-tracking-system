package com.example.event.producer;

import com.kafka.common.dto.OrderDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderDTO orderDTO) {
        kafkaTemplate.send("new-orders", orderDTO.getOrderId(), orderDTO);

    }

}
