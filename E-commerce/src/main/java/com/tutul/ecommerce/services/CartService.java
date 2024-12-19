package com.tutul.ecommerce.services;

import com.tutul.ecommerce.dto.CartDTO;
import com.tutul.ecommerce.entities.Cart;
import com.tutul.ecommerce.entities.Product;
import com.tutul.ecommerce.exception.ProductNotFoundException;
import com.tutul.ecommerce.repositories.CartRepository;
import com.tutul.ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;


@Service

public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart addToCart(CartDTO cartDTO) {

        Product product = productRepository.findById(cartDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: "));

        if (!product.getIsActive()) {
            throw new ProductNotFoundException("The Product is not available Anymore");
        }

        if (cartDTO.getQuantity() > product.getStock()) {
            throw new RuntimeException("Insufficient stock for product ID: ");
        }

        double discountedPrice = product.getPrice();
        if (product.getDiscount() != null && product.getDiscount() > 0) {
            discountedPrice -= (product.getPrice() * product.getDiscount() / 100);
        }

        // Calculate total price for the cart item
        double totalPrice = discountedPrice * cartDTO.getQuantity();

        // Update the product stock
        product.setStock(product.getStock() - cartDTO.getQuantity());
        productRepository.save(product);

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(cartDTO.getQuantity());
        cart.setTotalPrice(totalPrice);

        return cartRepository.save(cart);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
    }
}