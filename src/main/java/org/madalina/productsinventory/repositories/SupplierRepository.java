package org.madalina.productsinventory.repositories;

import org.madalina.productsinventory.dtoDB.SupplierDTO;
import org.madalina.productsinventory.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>{



}
