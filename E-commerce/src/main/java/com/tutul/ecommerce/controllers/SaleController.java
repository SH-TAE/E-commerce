package com.tutul.ecommerce.controllers;

import com.tutul.ecommerce.entities.Sale;
import com.tutul.ecommerce.services.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/daily-sales")
    public ResponseEntity<List<Sale>> getDailySales(@RequestParam String date) {
        LocalDate saleDate = LocalDate.parse(date);

        List<Sale> dailySales = saleService.getDailySales(saleDate);
        return ResponseEntity.ok(dailySales);
    }

    @PostMapping("/daily-single-sale")
    public ResponseEntity<Sale> recordSale(
            @RequestParam Long productId,
            @RequestParam int quantitySold) {

        Sale sale = saleService.recordSale(productId, quantitySold);
        return ResponseEntity.ok(sale);
    }

    @PostMapping("/multiple-sales")
    public ResponseEntity<List<Sale>> recordSales(@RequestBody List<Sale> sales) {
        List<Sale> recordedSales = saleService.recordSales(sales);
        return ResponseEntity.ok(recordedSales);
    }
}
