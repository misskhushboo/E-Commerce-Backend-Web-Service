package org.ace.hcl.orderbillingsystem.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatusRequest {

    private int orderId;
    private String orderStatus;
}
