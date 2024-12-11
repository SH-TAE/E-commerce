package com.tutul.ecommerce.dto;

import com.tutul.ecommerce.entities.Order;
import com.tutul.ecommerce.entities.OrderItems;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {

    private Order order;
    private List<OrderItems> orderItems;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }
}