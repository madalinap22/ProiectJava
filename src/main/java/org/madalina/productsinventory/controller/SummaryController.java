package org.madalina.productsinventory.controller;

import org.madalina.productsinventory.dto.Product;
import org.madalina.productsinventory.service.ProductsService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/summary")
public class SummaryController {
    private final ProductsService productsService;

    public SummaryController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/totalProducts")
    public ResponseEntity<String> getTotalNumberOfProducts() {
        String totalProducts = productsService.getTotalNumberOfProductsDB();
        return new ResponseEntity<>(totalProducts, HttpStatus.OK);
    }

    @GetMapping("/totalSales")
    public ResponseEntity<String> getProduseVandute() {
        String salesSummary = productsService.getProduseVandute();
        return new ResponseEntity<>(salesSummary, HttpStatus.OK);
    }

    @GetMapping("/sortSales")
    public ResponseEntity<List<Product>> getProduseSortateVanzari() {
        List<Product> produseSortate = productsService.getProduseSortateVanzari();
        return new ResponseEntity<>(produseSortate, HttpStatus.OK);
    }

    @GetMapping("/BBDcritic")
    public ResponseEntity<List<Product>> getBBDcritic() {
        List<Product> produse = productsService.getBBDcritic();
        return new ResponseEntity<>(produse, HttpStatus.OK);
    }

    @GetMapping("/stocCritic")
    public ResponseEntity<List<Product>> getStocCritic() {
        List<Product> produseCuStocCritic = productsService.getStocCritic();
        return new ResponseEntity<>(produseCuStocCritic, HttpStatus.OK);
    }

}
