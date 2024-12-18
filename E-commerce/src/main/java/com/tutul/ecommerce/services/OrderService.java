package com.tutul.ecommerce.services;

import com.tutul.ecommerce.dto.OrderItemsDTO;
import com.tutul.ecommerce.dto.OrderRequestDTO;
import com.tutul.ecommerce.entities.Orders;
import com.tutul.ecommerce.entities.OrderItems;
import com.tutul.ecommerce.entities.Product;
import com.tutul.ecommerce.exception.OrderNotFoundException;
import com.tutul.ecommerce.exception.ProductNotFoundException;
import com.tutul.ecommerce.repositories.OrderRepository;
import com.tutul.ecommerce.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Orders createOrder(OrderRequestDTO orderRequestDTO) {

        String address = orderRequestDTO.getAddress();
        Double shippingCost = orderRequestDTO.getShippingCost();
        List<OrderItemsDTO> orderItemsDTOList = orderRequestDTO.getOrderItems();

        if (orderItemsDTOList == null || orderItemsDTOList.isEmpty()) {
            throw new IllegalArgumentException("Order items list cannot be empty.");
        }

        Orders orders = new Orders();
        orders.setAddress(address);
        orders.setShippingCost(shippingCost);

        double totalPrice = 0.0;

        for (OrderItemsDTO orderItemDTO : orderItemsDTOList) {

            Long productId = orderItemDTO.getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

            OrderItems orderItem = new OrderItems();
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPrice(orderItemDTO.getPrice());
            orderItem.setProduct(product);
            orderItem.setOrders(orders);


            orders.getOrderItems().add(orderItem); // Add order item to order's item list

            totalPrice += orderItemDTO.getQuantity() * orderItemDTO.getPrice();
            System.err.println("I'm from Create order");
        }
        totalPrice += shippingCost;
        orders.setTotalPrice(totalPrice);

        System.err.println("Hlw");


        return orderRepository.save(orders); // Save the order along with order items
    }



    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }
}