package org.ace.hcl.orderbillingsystem.orderservice.models;

import lombok.*;
import org.ace.hcl.orderbillingsystem.orderservice.entities.Order;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@Getter
@Setter
public class OrderProcessed {

    private long orderId;
    private String status;

    public OrderProcessed(long orderId, String status){
        this.orderId=orderId;
        this.status=status;
    }
}
