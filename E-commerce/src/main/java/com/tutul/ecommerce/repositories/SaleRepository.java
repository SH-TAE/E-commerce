package com.tutul.ecommerce.repositories;

import com.tutul.ecommerce.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByProductIdAndSaleDate(Long productId, LocalDate saleDate);
    List<Sale> findBySaleDate(LocalDate saleDate);


}
