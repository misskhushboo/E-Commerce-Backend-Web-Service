package org.ace.hcl.orderbillingsystem.inventoryservice.repositories;

import org.ace.hcl.orderbillingsystem.inventoryservice.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends CrudRepository<Product, Long> {

    Optional<Product> findByProductName(String name);

    // @Query("Select * From Products Where Id In (:)")
    @Query(value = "SELECT p FROM Product p WHERE p.productId IN :productList")
    Optional<List<Product>> findProductByProductList(@Param("productList") List<Long> productList);
}
