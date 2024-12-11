package com.tutul.ecommerce.services;

import com.tutul.ecommerce.entities.Product;
import com.tutul.ecommerce.exception.DuplicateProductException;
import com.tutul.ecommerce.exception.ProductNotFoundException;
import com.tutul.ecommerce.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Page<Product> getAllActiveProducts(Pageable pageable) {
        return productRepository.findByIsActiveTrue(pageable);
    }


    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }


    public Product addProduct(Product product) {
        boolean exists = productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId());
        if (exists) {
            throw new DuplicateProductException("Product with title '" + product.getTitle() + " already exists in category ");
        }
        return productRepository.save(product);
    }


    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);
        product.setTitle(updatedProduct.getTitle());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());
        product.setIsActive(updatedProduct.getIsActive());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        product.setIsActive(false);
        productRepository.save(product);
    }


    public void adjustStock(Long productId, int quantity) throws InsufficientResourcesException {
        Product product = getProductById(productId);
        int newStock = product.getStock() - quantity;
        if (newStock < 0) {
            throw new InsufficientResourcesException("Insufficient stock for product: " + product.getTitle());
        }
        product.setStock(newStock);
        productRepository.save(product);
    }


    public Product getProductWithDiscount(Long id, Integer discount) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (discount != null && discount > 0) {
            Double discountedPrice = product.getPrice() - (product.getPrice() * discount / 100);
            product.setPrice(discountedPrice); // Update price temporar
        }

        return product; // Return after updating the price
    }

}