package com.tutul.ecommerce.controllers;


import com.tutul.ecommerce.dto.OrderRequestDTO;
import com.tutul.ecommerce.entities.Orders;
import com.tutul.ecommerce.entities.OrderItems;
import com.tutul.ecommerce.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public Orders createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        Orders orders = orderRequestDTO.getOrders(); // Extract order and order items from the DTO

        List<OrderItems> orderItemsList = orderRequestDTO.getOrderItems();

        return orderService.createOrder(orders, orderItemsList);
    }

    @GetMapping("/orders")
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("orders/{id}")
    public Orders getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
