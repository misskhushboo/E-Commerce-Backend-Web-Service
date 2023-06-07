package org.ace.hcl.orderbillingsystem.inventoryservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ace.hcl.orderbillingsystem.inventoryservice.entities.Catalogue;

import java.util.ArrayList;

@Setter
@Getter
@ToString
public class CatalogueInfo {

    private String catalogueName;
    private ArrayList<ProductsInfo> productsInfoList;

    public CatalogueInfo() {
    }
}
