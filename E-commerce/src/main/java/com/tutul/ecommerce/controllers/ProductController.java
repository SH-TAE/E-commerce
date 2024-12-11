package com.tutul.ecommerce.controllers;

import com.tutul.ecommerce.entities.Product;
import com.tutul.ecommerce.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Page<Product> getAllProducts(Pageable pageable) {
        return productService.getAllActiveProducts(pageable);
    }

    @GetMapping("products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/product/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping("admin/adjust-stock/{id}")
    public void adjustStock(@PathVariable Long id, @RequestParam int quantity) throws InsufficientResourcesException {
        productService.adjustStock(id, quantity);
    }

    @GetMapping("/{id}/discounted")
    public Product getProductWithDiscount(@PathVariable Long id, @RequestParam(required = false) Integer discount) {//Ensuring the API does not throw an error when the parameter is missing.
        return productService.getProductWithDiscount(id, discount);
    }
}

