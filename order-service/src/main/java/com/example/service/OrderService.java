package com.example.service;

import com.example.model.Order;
import com.example.event.producer.OrderProducer;
import com.example.repository.OrderRepository;
import com.kafka.common.dto.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private OrderProducer producer;
    ModelMapper mapper = new ModelMapper();

    public void process(Order order) {
        saveOrder(order);
        OrderDTO orderDTO = mapper.map(order, OrderDTO.class);
        producer.sendOrder(orderDTO);
    }

    public void saveOrder(Order order) {
        repository.save(order);
    }
}
