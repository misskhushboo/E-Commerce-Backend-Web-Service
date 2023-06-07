package org.ace.hcl.orderbillingsystem.orderservice.repositories;

import org.ace.hcl.orderbillingsystem.orderservice.entities.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByOrderId(long orderId);
    Optional<List<Order>> findByUsername(String username);
}
