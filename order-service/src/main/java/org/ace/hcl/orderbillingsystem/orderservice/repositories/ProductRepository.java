package org.ace.hcl.orderbillingsystem.orderservice.repositories;


import org.ace.hcl.orderbillingsystem.orderservice.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
