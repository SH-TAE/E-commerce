package com.tutul.ecommerce.repositories;

import com.tutul.ecommerce.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}