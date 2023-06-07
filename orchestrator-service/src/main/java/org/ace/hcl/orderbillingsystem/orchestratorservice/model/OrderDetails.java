package org.ace.hcl.orderbillingsystem.orchestratorservice.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class OrderDetails implements Serializable {

    private long orderId;
    private List<ProductsInfo> products;
    private String status;

    //private String username;
}
