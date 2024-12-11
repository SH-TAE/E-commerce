package com.tutul.ecommerce.repositories;

import com.tutul.ecommerce.entities.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
}
