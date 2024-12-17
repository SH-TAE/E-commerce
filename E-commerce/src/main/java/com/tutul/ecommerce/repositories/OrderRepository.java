package com.tutul.ecommerce.repositories;

import com.tutul.ecommerce.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
