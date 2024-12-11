package com.tutul.ecommerce.services;

import com.tutul.ecommerce.entities.Order;
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
    public Order createOrder(Order order, List<OrderItems> orderItemsList) {
        double totalPrice = 0.0;
        for (OrderItems orderItem : orderItemsList) {
            totalPrice += orderItem.getQuantity() * orderItem.getPrice();
        }
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        for (OrderItems orderItem : orderItemsList) {
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        }

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }
}