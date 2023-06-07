package org.ace.hcl.orderbillingsystem.inventoryservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.ace.hcl.orderbillingsystem.inventoryservice.entities.Catalogue;
import org.ace.hcl.orderbillingsystem.inventoryservice.entities.Product;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductsInfo implements Serializable, Cloneable {

    private long productId;
    private String productName;
    private int rating;
    private double price;
    private double discount;
    private int quantity;
    private String catalogueName;

    public ProductsInfo(Product product, String catalogueName){
        BeanUtils.copyProperties(product,this);
        this.catalogueName=catalogueName;
    }
}
