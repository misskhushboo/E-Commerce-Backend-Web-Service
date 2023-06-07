package org.ace.hcl.orderbillingsystem.orderservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ace.hcl.orderbillingsystem.orderservice.models.ProductsInfo;
import org.springframework.beans.BeanUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="ORDER_PRODUCTS")
public class Product {

    @Id
    @Column(name="product_id")
    private long productId;

    @Column(name="product_price")
    private double price;

    @Column(name="product_quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Product(ProductsInfo productsInfo, Order order){

        BeanUtils.copyProperties(productsInfo, this);
        this.order=order;
    }
}
