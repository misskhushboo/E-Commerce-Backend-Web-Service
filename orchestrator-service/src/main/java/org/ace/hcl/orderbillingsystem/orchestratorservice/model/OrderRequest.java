package org.ace.hcl.orderbillingsystem.orchestratorservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderRequest {
    private List<ProductsInfo> products;
    String username;
}
