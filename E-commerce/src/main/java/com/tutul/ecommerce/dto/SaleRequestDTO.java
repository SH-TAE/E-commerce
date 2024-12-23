package com.tutul.ecommerce.dto;

import com.tutul.ecommerce.entities.Sale;
import lombok.Data;

import java.util.List;

@Data
public class SaleRequestDTO {
    private List<Sale> sales;
    private List<Integer> quantitiesSold;
    private Long productId;


}
