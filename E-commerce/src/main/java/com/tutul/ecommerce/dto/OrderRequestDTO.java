package com.tutul.ecommerce.dto;

import com.tutul.ecommerce.entities.Orders;
import com.tutul.ecommerce.entities.OrderItems;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {

    private String address;

    private Double shippingCost;

    private List<OrderItemsDTO> orderItems;

}