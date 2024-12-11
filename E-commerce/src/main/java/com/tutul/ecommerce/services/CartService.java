package com.tutul.ecommerce.services;

import com.tutul.ecommerce.entities.Cart;
import com.tutul.ecommerce.repositories.CartRepository;
import org.springframework.stereotype.Service;


@Service

public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart addToCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
    }
}