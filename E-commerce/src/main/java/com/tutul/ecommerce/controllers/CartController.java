package com.tutul.ecommerce.controllers;

import com.tutul.ecommerce.dto.CartDTO;
import com.tutul.ecommerce.entities.Cart;
import com.tutul.ecommerce.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-or-update")
    public ResponseEntity<Cart> addOrUpdateCart(@RequestBody CartDTO cartDTO) {
        Cart updatedCart = cartService.addOrUpdateCart(cartDTO);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        return ResponseEntity.ok(cart);
    }

}