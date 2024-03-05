package org.madalina.productsinventory.repositories;

import org.madalina.productsinventory.dtoDB.SupplierDTO;
import org.madalina.productsinventory.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>{


    @Query(value="SELECT \n" +
            "    s.id, \n" +
            "    s.name, \n" +
            "    s.contact_info, \n" +
            "    SUM(p.cantitate_achizitionata) AS total_cantitate_achizitionata, \n" +
            "    SUM(p.pret_achizitie * p.cantitate_achizitionata) AS total_valoare_achizitie\n" +
            "FROM \n" +
            "    \"madalina-schema\".suppliers s \n" +
            "JOIN \n" +
            "    \"madalina-schema\".products p ON s.id = p.supplier_id \n" +
            "GROUP BY \n" +
            "    s.id, s.name, s.contact_info;", nativeQuery = true)
    List<Object[]> findSuppliersWithProductSummary();  //lista de obiecte de tipul Object[] = query returneaza valori calculate si info din mai multe tabele



//    @Query("SELECT new org.madalina.productsinventory.dtoDB.SupplierDTO(s.id, s.name, s.contactInfo, SUM(p.cantitateAchizitionata), SUM(p.pretAchizitie * p.cantitateAchizitionata)) " +
//            "FROM Supplier s JOIN s.products p GROUP BY s.id, s.name, s.contactInfo")
//    List<SupplierDTO> findSuppliersWithProductSummary();

}
