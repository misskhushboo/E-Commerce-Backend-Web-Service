package org.ace.hcl.orderbillingsystem.inventoryservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Catalogue")
public class Catalogue {
    @Id //PK for this, this is based on Join column
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="catalogue_id")
    private long catalogueId;
    @Column(name="catalogue_name")
    private String catalogueName;
    @OneToMany(mappedBy = "catalogue")//comment this and try
    private List<Product> products;
    public Catalogue(String catalogueName){
        this.catalogueName=catalogueName;
    }
}
