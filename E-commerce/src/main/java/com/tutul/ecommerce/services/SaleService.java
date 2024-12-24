package com.tutul.ecommerce.services;


import com.tutul.ecommerce.entities.Product;
import com.tutul.ecommerce.entities.Sale;
import com.tutul.ecommerce.exception.InsufficientStockException;
import com.tutul.ecommerce.exception.ProductNotFoundException;
import com.tutul.ecommerce.repositories.ProductRepository;
import com.tutul.ecommerce.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }


    // * // For a single item sell-record
    public Sale recordSale(Long productId, int quantitySold) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (quantitySold > product.getStock()) {
            throw new InsufficientStockException("Insufficient stock for sale");
        }
        product.setStock(product.getStock() - quantitySold);
        productRepository.save(product);

        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setQuantitySold(quantitySold);
        sale.setSaleDate(LocalDate.now());

        return saleRepository.save(sale);
    }
   // * // for a List-of sale recodos
    public List<Sale> recordSales(List<Sale> sales) {
        for (Sale sale : sales) {
            Product product = productRepository.findById(sale.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            if (sale.getQuantitySold() > product.getStock()) {
                throw new InsufficientStockException("Insufficient stock for sale");
            }
            product.setStock(product.getStock() - sale.getQuantitySold());
            productRepository.save(product);
        }

        return saleRepository.saveAll(sales);
    }


    public List<Sale> getDailySales(LocalDate saleDate) {
        return saleRepository.findBySaleDate(saleDate);
    }
}