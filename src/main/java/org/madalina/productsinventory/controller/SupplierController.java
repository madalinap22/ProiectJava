package org.madalina.productsinventory.controller;

import org.madalina.productsinventory.dtoDB.SupplierDTO;
import org.madalina.productsinventory.entities.Supplier;
import org.madalina.productsinventory.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.findAllSuppliers();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @GetMapping("/{summary}")
    public ResponseEntity<List<SupplierDTO>> getSuppliersSummary() {
        List<SupplierDTO> summaries = supplierService.getSuppliersSummary();
        return ResponseEntity.ok(summaries);
    }


}
