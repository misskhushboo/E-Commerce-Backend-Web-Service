package org.ace.hcl.orderbillingsystem.paymentservice.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetails {

    private long orderId;
    private List<ProductsInfo> products;
    private String status;

    private String username;

}
