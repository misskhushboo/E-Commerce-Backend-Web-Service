package org.ace.hcl.orderbillingsystem.orchestratorservice.model;

import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductsInfo {

    private long productId;
    private int quantity;
}
