package org.madalina.productsinventory.controller;

import org.madalina.productsinventory.entities.Product;
import org.madalina.productsinventory.service.ProductsService;
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
    public ResponseEntity<String> getTotalNumberOfProductsDB() {
        String totalProducts = productsService.getTotalNumberOfProductsDB();
        return new ResponseEntity<>(totalProducts, HttpStatus.OK);
    }

    @GetMapping("/totalSales")
    public ResponseEntity<String> getProduseVanduteDB() {
        String salesSummary = productsService.getProduseVanduteDB();
        return new ResponseEntity<>(salesSummary, HttpStatus.OK);
    }

    @GetMapping("/sortSales")
    public ResponseEntity<List<Product>> getProduseSortateVanzariDB() {
        List<Product> produseSortate = productsService.getProduseSortateVanzariDB();
        return new ResponseEntity<>(produseSortate, HttpStatus.OK);
    }

    @GetMapping("/BBDcritic")
    public ResponseEntity<List<Product>> getBBDcriticDB() {
        List<Product> bbdCritic = productsService.getBBDcriticDB();
        return new ResponseEntity<>(bbdCritic, HttpStatus.OK);
    }

    @GetMapping("/stocCritic")
    public ResponseEntity<List<Product>> getStocCriticDB() {
        List<Product> produseCuStocCritic = productsService.getStocCriticDB();
        return new ResponseEntity<>(produseCuStocCritic, HttpStatus.OK);
    }

}
