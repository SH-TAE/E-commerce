package com.tutul.ecommerce.specification;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProductFilterParams {

    private String title;

    private String description;

    private String category;

    private Double price;


}
