package org.ace.hcl.orderbillingsystem.orchestratorservice.model;

import lombok.*;

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
