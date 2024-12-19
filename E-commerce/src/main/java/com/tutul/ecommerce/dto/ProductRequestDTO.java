package com.tutul.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private String title;

    private String description;

    private Double price;

    private Integer stock;

    private Boolean isActive;

    private Integer discount;

    private Long categoryId;
}
