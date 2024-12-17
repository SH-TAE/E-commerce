package com.tutul.ecommerce.dto;

import com.tutul.ecommerce.entities.Orders;
import com.tutul.ecommerce.entities.OrderItems;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {

    private Orders orders;
    private List<OrderItems> orderItems;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }
}