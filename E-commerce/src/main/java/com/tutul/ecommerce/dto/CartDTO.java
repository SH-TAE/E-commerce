package com.tutul.ecommerce.dto;

import lombok.Data;

import java.util.List;


@Data
public class CartDTO {

    private Long cartId;

    private List<CartItemDTO> items;

}
