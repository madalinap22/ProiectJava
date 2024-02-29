package org.madalina.productsinventory.service;

import org.madalina.productsinventory.entities.Supplier;
import org.madalina.productsinventory.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> findAllSuppliers() {
        return supplierRepository.findAll();
    }



}
