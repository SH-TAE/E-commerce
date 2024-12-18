package com.tutul.ecommerce.dto;

import lombok.Data;

@Data
public class OrderItemsDTO {

    private Long productId;

    private int quantity;

    private Double price;

}

