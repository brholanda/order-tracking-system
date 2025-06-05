package com.example.service;

import com.example.model.Order;
import com.example.producer.OrderProducer;
import com.example.repository.OrderRepository;
import com.example.service.mapper.OrderEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private OrderProducer producer;

    public void process(Order order) {
        repository.save(order);
        producer.sendOrder(OrderEventMapper.map(order));
    }
}
