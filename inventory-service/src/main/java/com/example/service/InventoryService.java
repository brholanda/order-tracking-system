package com.example.service;

import com.example.event.producer.InventoryProducer;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.kafka.common.dto.OrderDTO;
import com.kafka.common.dto.OrderStatus;
import com.kafka.common.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private InventoryProducer producer;

    public void process(OrderDTO orderDTO) {
        List<Product> products = getProductsFromDatabase(orderDTO);

        if (!hasSufficientStock(orderDTO, products)) {
            orderDTO.setStatus(OrderStatus.ORDER_CANCELLED);
            sendOrder(orderDTO);
            return;
        }

        updateStock(orderDTO, products);
        repository.saveAll(products);

        orderDTO.setStatus(OrderStatus.ORDER_COMPLETED);
        sendOrder(orderDTO);
    }

    private List<Product> getProductsFromDatabase(OrderDTO orderDTO) {
        List<String> ids = orderDTO.getProducts().stream()
                .map(ProductDTO::getId)
                .toList();
        return repository.findAllById(ids);
    }

    private boolean hasSufficientStock(OrderDTO orderDTO, List<Product> stockProducts) {
        Map<String, Integer> orderQuantities = orderDTO.getProducts().stream()
                .collect(Collectors.toMap(ProductDTO::getId, ProductDTO::getQuantity));

        return !isEmpty(stockProducts) && stockProducts.stream().allMatch(product ->
                product.getAvailableQuantity() >= orderQuantities.getOrDefault(product.getId(), 0)
        );
    }

    private void updateStock(OrderDTO orderDTO, List<Product> stockProducts) {
        Map<String, Integer> quantities = orderDTO.getProducts().stream()
                .collect(Collectors.toMap(ProductDTO::getId, ProductDTO::getQuantity));

        stockProducts.forEach(product -> {
            int orderQuantity = quantities.getOrDefault(product.getId(), 0);
            product.setAvailableQuantity(product.getAvailableQuantity() - orderQuantity);
        });
    }

    private void sendOrder(OrderDTO orderDTO) {
        producer.sendOrder(orderDTO);
    }
}
