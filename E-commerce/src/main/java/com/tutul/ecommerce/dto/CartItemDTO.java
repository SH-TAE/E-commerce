package com.tutul.ecommerce.dto;

import lombok.Data;


@Data
public class CartItemDTO {

    private Long productId;

    private Integer quantity;
}
