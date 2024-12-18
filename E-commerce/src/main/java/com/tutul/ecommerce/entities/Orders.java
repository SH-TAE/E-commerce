package com.tutul.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String address;

    private Double shippingCost;

    private Double totalPrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders")
    private List<OrderItems> orderItems = new ArrayList<>();



}

