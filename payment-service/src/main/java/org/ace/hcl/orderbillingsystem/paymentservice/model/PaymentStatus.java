package org.ace.hcl.orderbillingsystem.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PaymentStatus {

    private int orderId;
    private String status;
}
