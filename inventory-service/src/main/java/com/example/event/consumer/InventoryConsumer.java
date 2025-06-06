package com.example.event.consumer;

import com.example.service.InventoryService;
import com.kafka.common.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

    private static final Logger logger = LoggerFactory.getLogger(InventoryConsumer.class);

    @Autowired
    private InventoryService service;

    @KafkaListener(topics = "payment-processed", groupId = "inventory-group")
    public void consume(OrderDTO orderDTO) {
        logger.info("Order received: {}", orderDTO.getOrderId());
        service.process(orderDTO);
    }
}
