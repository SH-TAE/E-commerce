package com.tutul.ecommerce.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CartDTO {

    private Long productId;

    private Integer quantity;

}
