package org.madalina.productsinventory.controller;

import org.madalina.productsinventory.dto.Product;
import org.madalina.productsinventory.dtoDB.ProductDTO;
import org.madalina.productsinventory.service.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http://localhost:8080/api/v1/products
@RestController //la nivelul clasei definește calea de bază pentru toate metodele din controller. Asta înseamnă că toate metodele din acest controller vor fi accesate printr-o URL care începe cu /products.
@RequestMapping("/products")  //la nivelul clasei definește calea de bază pentru toate metodele din controller. Asta înseamnă că toate metodele din acest controller vor fi accesate printr-o URL care începe cu /products.
public class ProductsController {

    private final ProductsService productsService;
    public ProductsController(ProductsService productsService){
        this.productsService = productsService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productsService.getProduseDB();
    }

    //obtinem datele unui produs cu un anumit ID
    @GetMapping("/{id}")
//    @RequestMapping("/{id}")
    public ProductDTO getProductById2(@PathVariable("id") int id){
        return productsService.getProductById2(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") int id) {
        boolean isDeleted = productsService.deleteProductByIdDB(id);
        if (isDeleted) {
            return ResponseEntity.ok().body("Product with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found.");
        }
    }

    @PostMapping      //http://localhost:8080/products  cu POST BODY raw Text->Json
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO){
        productsService.createProduct(productDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = productsService.updateProductDB(id, productDTO);
        return ResponseEntity.ok(updatedProductDTO); // Returnează DTO-ul actualizat + un raspuns OK
    }





}
