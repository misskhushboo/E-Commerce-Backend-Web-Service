package org.ace.hcl.orderbillingsystem.orderservice.models;

import lombok.*;
import org.ace.hcl.orderbillingsystem.orderservice.entities.Product;
import org.springframework.beans.BeanUtils;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsInfo {

    private long productId;
    private int quantity;

    public ProductsInfo(Product product){
        BeanUtils.copyProperties(product,this);
    }
}
