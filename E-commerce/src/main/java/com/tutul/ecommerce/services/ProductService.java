package com.tutul.ecommerce.services;

import com.tutul.ecommerce.dto.ProductRequestDTO;
import com.tutul.ecommerce.entities.Category;
import com.tutul.ecommerce.entities.Product;
import com.tutul.ecommerce.exception.DuplicateProductException;
import com.tutul.ecommerce.exception.InvalidProductPriceException;
import com.tutul.ecommerce.exception.ProductNotFoundException;
import com.tutul.ecommerce.repositories.CategoryRepository;
import com.tutul.ecommerce.repositories.ProductRepository;
import com.tutul.ecommerce.specification.ProductFilterParams;
import com.tutul.ecommerce.specification.ProductFilterSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.naming.InsufficientResourcesException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    public Page<Product> getAllActiveProducts(String title, String description, String category, Double price, int page, int size) {
        System.err.println("invoked");
        Specification<Product> specification = ProductFilterSpecification.getFilteredProduct(new ProductFilterParams(title, description, category, price));
        Pageable pageable = PageRequest.of(page, size);
        return (Page<Product>) productRepository.findAll(specification, pageable);
    }


    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }


    public Product addProduct(ProductRequestDTO productRequestDTO) {
        Long categoryId = productRequestDTO.getCategoryId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        boolean exists = productRepository.existsByTitleAndCategoryId(productRequestDTO.getTitle(), categoryId);
        if (exists) {
            throw new DuplicateProductException("Product with title '" + productRequestDTO.getTitle() + "' already exists in category.");
        }

        if (productRequestDTO.getDiscount() != null && productRequestDTO.getDiscount() > 0) {
            Double originalPrice = productRequestDTO.getPrice();
            double discountedPrice = originalPrice - (originalPrice * productRequestDTO.getDiscount() / 100);

            // * // Cannot give discount more than 50% on a product
            if (discountedPrice < originalPrice * 0.50) {
                throw new InvalidProductPriceException("Price cannot be reduced by more than 50%");
            }
        }
        // * // map to my created Dto
        Product product = new Product();
        product.setTitle(productRequestDTO.getTitle());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setStock(productRequestDTO.getStock());
        product.setIsActive(productRequestDTO.getIsActive());
        product.setDiscount(productRequestDTO.getDiscount());
        product.setCategory(category);

        return productRepository.save(product);
    }


    public Product updateProduct(Long id, ProductRequestDTO updatedProduct) {
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
        int newStock = product.getStock() + quantity;
        if (newStock < 50) {
            throw new InsufficientResourcesException("Insufficient stock for product: " + product.getTitle());
        }
        product.setStock(newStock);
        productRepository.save(product);
    }


    public Product getProductWithDiscount(Long id, Integer discount) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (discount != null && discount > 0) {
            Double originalPrice = product.getPrice();
            double discountedPrice = originalPrice - (originalPrice * discount / 100);

            if (discountedPrice < originalPrice * 0.50) {
                throw new InvalidProductPriceException("Price cannot be reduced by more than 50%");
            }
            product.setPrice(discountedPrice);
        }

        return product;
    }

}
