package com.tutul.ecommerce.services;

import com.tutul.ecommerce.entities.Orders;
import com.tutul.ecommerce.entities.OrderItems;
import com.tutul.ecommerce.exception.OrderNotFoundException;
import com.tutul.ecommerce.repositories.OrderItemRepository;
import com.tutul.ecommerce.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public Orders createOrder(Orders orders, List<OrderItems> orderItemsList) {
        double totalPrice = 0.0;
        for (OrderItems orderItem : orderItemsList) {
            totalPrice += orderItem.getQuantity() * orderItem.getPrice();
            Integer quantity = orderItem.getQuantity();
            System.err.println("Quantity: " + quantity);
        }
        orders.setTotalPrice(totalPrice);

        Orders savedOrders = orderRepository.save(orders);

        for (OrderItems orderItem : orderItemsList) {
            orderItem.setOrders(savedOrders);
            orderItemRepository.save(orderItem);
        }

        return savedOrders;
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }
}