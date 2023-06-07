package org.ace.hcl.orderbillingsystem.inventoryservice.entities;

import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Products")
public class Product {
    //class name would be singular
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="product_id")
    private long productId;
    @Column(name="product_name")
    private String productName;

    @Column(name="product_rating")
    private int rating;

    @Column(name="product_price")
    private double price;

    @Column(name="product_discount")
    private double discount;

    @Column(name="product_quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "catalogue_id")
    private Catalogue catalogue;
    //It simply means that our Product entity will have a foreign key column named
    // catalogue_id referring to the primary attribute id of the Catalogue entity.
    //This is owning side (many side which has the Foreign key of the entity).


}
