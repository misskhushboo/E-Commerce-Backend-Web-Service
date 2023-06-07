package org.ace.hcl.orderbillingsystem.orderservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ace.hcl.orderbillingsystem.orderservice.models.OrderRequest;
import org.ace.hcl.orderbillingsystem.orderservice.models.ProductsInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Order")
public class Order {

    @Id
    @GeneratedValue
    private long orderId;
    @OneToMany(mappedBy = "order")
    private List<Product> products;
    private String username;
    private String status;

    public Order(String username){
        this.username=username;
    }

}