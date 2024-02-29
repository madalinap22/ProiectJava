package org.madalina.productsinventory.repositories;

import org.madalina.productsinventory.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {  //Product clasa adnotata cu @Entity si tipul id-ului

    //pt SummaryCollector
    @Query(value="SELECT COUNT(p) FROM \"madalina-schema\".\"products\" p;", nativeQuery = true)
    long countTotalProducts();

    @Query(value="SELECT SUM(p.pret_achizitie) FROM \"madalina-schema\".\"products\" p;",nativeQuery = true)
    Double sumTotalValue();

    @Query(value = "SELECT SUM(p.cantitate_vanduta) FROM \"madalina-schema\".\"products\" p",
            nativeQuery = true)
    Integer getTotalCantitateVanduta();

    @Query(value = "SELECT SUM(p.pret_vanzare * p.cantitate_vanduta) FROM \"madalina-schema\".\"products\" p",
            nativeQuery = true)
    Double getValoareTotalaVanzari();


    @Query(value = "SELECT * FROM \"madalina-schema\".\"products\" ORDER BY cantitate_vanduta DESC LIMIT 3",
            nativeQuery = true)
    List<Product> topCantitateVanduta();

    @Query(value = "SELECT * FROM \"madalina-schema\".\"products\" " +
            "WHERE bbd - CURRENT_DATE <= 60 AND bbd > CURRENT_DATE", nativeQuery = true)
    List<Product> findBBDcritic();

    @Query(value = "SELECT * FROM \"madalina-schema\".\"products\" " +
            "WHERE (cantitate_achizitionata - cantitate_vanduta) < 100", nativeQuery = true)
    List<Product> findProduseCuStocCritic();






    // using explicit JPQL
//    @Query("SELECT u FROM users u WHERE u.name = :name")
//    List<User> findUsersByNameJPQL(String name);

    // using native SQL
//    @Query(value = "SELECT COUNT(*) FROM products", nativeQuery = true)
//    Long countTotalProducts();




}
