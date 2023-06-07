package org.ace.hcl.orderbillingsystem.orderservice.models;

import jakarta.persistence.Id;
import lombok.*;
import org.ace.hcl.orderbillingsystem.orderservice.models.ProductsInfo;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {

    private List<ProductsInfo> products;
    String username;

}
