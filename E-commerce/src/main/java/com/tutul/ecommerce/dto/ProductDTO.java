package com.tutul.ecommerce.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductDTO {

    private Long id;

    private String title;

    private Double price;

    private Integer stock;

    private Double discountedPrice;

}
