package com.example.event.consumer;

import com.example.model.Order;
import com.example.service.OrderService;
import com.kafka.common.dto.OrderDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    private OrderService service;
    ModelMapper mapper = new ModelMapper();

    @KafkaListener(topics = {"payment-processed", "payment-denied", "inventory-processed"}, groupId = "order-group")
    public void consume(OrderDTO orderDTO) {
        logger.info("Updating order: {}", orderDTO.getOrderId());
        Order order = mapper.map(orderDTO, Order.class);
        service.saveOrder(order);
        logger.info("Order {} updated", orderDTO.getOrderId());
    }
}
