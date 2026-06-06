package com.example.order_service.controller;
import com.example.order_service.model.Order;
import com.example.order_service.repo.OrderRepository;
import com.example.order_service.dto.UserDto;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repo;
    private final RestTemplate restTemplate = new RestTemplate();

    public OrderController(OrderRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return repo.save(order);
    }

    @GetMapping("/{id}")
    public Map getOrder(@PathVariable Long id) {

        Order order = repo.findById(id).orElse(null);

        String url = "http://localhost:8081/users/" + order.getUserId();

        UserDto user = restTemplate.getForObject(url, UserDto.class);

        return Map.of(
                "order", order,
                "user", user
        );
    }
}