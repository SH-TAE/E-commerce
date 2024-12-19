package com.tutul.ecommerce.controllers;

import com.tutul.ecommerce.dto.CartDTO;
import com.tutul.ecommerce.entities.Cart;
import com.tutul.ecommerce.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart")
    public ResponseEntity<Cart> addToCart(@RequestBody CartDTO cartDTO) {
        Cart cart = cartService.addToCart(cartDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @GetMapping("/cart/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @PutMapping("/cart/{cartId}")
    public ResponseEntity<Cart> updateCart(
            @PathVariable Long cartId,
            @RequestBody CartDTO cartDTO) {
        Cart updatedCart = cartService.updateCart(cartId, cartDTO);
        return ResponseEntity.ok(updatedCart);
    }
    
}
