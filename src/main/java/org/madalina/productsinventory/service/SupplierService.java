package org.madalina.productsinventory.service;

import org.madalina.productsinventory.dtoDB.SupplierDTO;
import org.madalina.productsinventory.entities.Product;
import org.madalina.productsinventory.entities.Supplier;
import org.madalina.productsinventory.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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

    public List<SupplierDTO> getSuppliersSummary() {
        List<Object[]> results = supplierRepository.findSuppliersWithProductSummary();
        List<SupplierDTO> summaries = new ArrayList<>();
        for (Object[] result : results) {
            int id = (int) result[0];
            String name = (String) result[1];
            String contactInfo = (String) result[2];
            Long totalQuantity = (Long) result[3];
            Double totalValue = (Double) result[4];

            summaries.add(new SupplierDTO(id, name,contactInfo, totalQuantity, totalValue));
        }
        return summaries;
    }






}
