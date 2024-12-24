package com.tutul.ecommerce.services;

import com.tutul.ecommerce.dto.CartDTO;
import com.tutul.ecommerce.dto.CartItemDTO;
import com.tutul.ecommerce.entities.Cart;
import com.tutul.ecommerce.entities.CartItem;
import com.tutul.ecommerce.entities.Product;
import com.tutul.ecommerce.exception.InsufficientStockException;
import com.tutul.ecommerce.exception.ProductNotFoundException;
import com.tutul.ecommerce.repositories.CartRepository;
import com.tutul.ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;


@Service

public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartRepository cartItemRepository1) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart addOrUpdateCart(CartDTO cartDTO) {
        Cart cart;

        if (cartDTO.getCartId() != null) {
            cart = cartRepository.findById(cartDTO.getCartId())
                    .orElseThrow(() -> new RuntimeException("Cart not found"));
        } else {
            cart = new Cart();
        }

        double totalCartPrice = 0.0;

        for (CartItemDTO itemDTO : cartDTO.getItems()) {

            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            if (itemDTO.getQuantity() > product.getStock()) {
                throw new InsufficientStockException("Insufficient stock for product ID: " + product.getId());
            }

            CartItem existingItem = null;
            for (CartItem item : cart.getItems()) {
                if (item.getProduct().getId().equals(itemDTO.getProductId())) {
                    existingItem = item;
                    break;
                }
            }

            if (existingItem != null) {

                int quantityDifference = itemDTO.getQuantity() - existingItem.getQuantity();
                product.setStock(product.getStock() - quantityDifference);
                existingItem.setQuantity(itemDTO.getQuantity());
                existingItem.setTotalPrice(product.getPrice() * itemDTO.getQuantity());
            } else {
                product.setStock(product.getStock() - itemDTO.getQuantity());

                CartItem newItem = new CartItem();
                newItem.setCart(cart);
                newItem.setProduct(product);
                newItem.setQuantity(itemDTO.getQuantity());
                newItem.setTotalPrice(product.getPrice() * itemDTO.getQuantity());

                cart.getItems().add(newItem);
            }

            totalCartPrice += product.getPrice() * itemDTO.getQuantity();
        }

        cart.setTotalCartPrice(totalCartPrice);

        for (CartItem item : cart.getItems()) {
            productRepository.save(item.getProduct());
        }
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));
    }

}