package org.madalina.productsinventory.repositories;

import org.madalina.productsinventory.dto.Product;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {  //Product clasa adnotata cu @Entity si tipul id-ului

    // pt totalProduse
//    @Query(value="SELECT COUNT(p) FROM \"madalina-schema\".\"products\" p;", nativeQuery = true)
//    long countTotalProducts();
//
//    @Query("SELECT SUM(p.pret_achizitie) FROM \"madalina-schema\".\"products\" p;")
//    Double sumTotalValue();

    // using explicit JPQL
//    @Query("SELECT u FROM users u WHERE u.name = :name")
//    List<User> findUsersByNameJPQL(String name);

    // using native SQL
    @Query(value = "SELECT COUNT(*) FROM products", nativeQuery = true)
    Long countTotalProducts();




}
