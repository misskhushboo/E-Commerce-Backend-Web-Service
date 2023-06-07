package org.ace.hcl.orderbillingsystem.paymentservice.model;

import lombok.*;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsInfo implements Serializable, Cloneable {

    private long productId;
    private int quantity;
}
