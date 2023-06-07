package org.ace.hcl.orderbillingsystem.inventoryservice.repositories;

import org.ace.hcl.orderbillingsystem.inventoryservice.entities.Catalogue;
import org.ace.hcl.orderbillingsystem.inventoryservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {

    Optional<Catalogue> findByCatalogueName(String name);
    Optional<Catalogue> findByCatalogueId(long catalogueId);

}
