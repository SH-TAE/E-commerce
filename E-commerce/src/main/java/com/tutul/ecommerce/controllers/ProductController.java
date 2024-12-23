package com.tutul.ecommerce.controllers;

import com.tutul.ecommerce.dto.ProductRequestDTO;
import com.tutul.ecommerce.entities.Product;
import com.tutul.ecommerce.entities.Sale;
import com.tutul.ecommerce.services.ProductService;
import com.tutul.ecommerce.services.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {


    private final ProductService productService;
    private final SaleService saleService;

    public ProductController(ProductService productService, SaleService saleService) {
        this.productService = productService;
        this.saleService = saleService;
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(required = false) String title,
                                                        @RequestParam(required = false) String description,
                                                        @RequestParam(required = false) String category,
                                                        @RequestParam(required = false) Double price,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "3") int size) {
        Page<Product> products = productService.getAllActiveProducts(title, description, category, price, page, size);
        return ResponseEntity.ok(products);
    }


    @GetMapping("products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }


    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        Product product = productService.addProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }


    @PutMapping("/product/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }


    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


    @PostMapping("admin/adjust-stock/{id}")
    public void adjustStock(@PathVariable Long id, @RequestParam int quantity)  {
        productService.adjustStock(id, quantity);
    }

    @GetMapping("/{id}/discounted")
    public Product getProductWithDiscount(@PathVariable Long id, @RequestParam(required = false) Integer discount) {//Ensuring the API does not throw an error when the parameter is missing.
        return productService.getProductWithDiscount(id, discount);
    }


    @GetMapping("/predict-restocking-for-a-month/{id}")
    public int predictRestocking(@PathVariable Long id) {
        return productService.predictRestocking(id);
    }

}


