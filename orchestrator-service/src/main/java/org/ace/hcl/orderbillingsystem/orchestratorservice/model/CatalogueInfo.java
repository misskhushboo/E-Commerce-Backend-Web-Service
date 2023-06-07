package org.ace.hcl.orderbillingsystem.orchestratorservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class CatalogueInfo {

    private String catalogueName;
    private ArrayList<Long> productIds;
}
