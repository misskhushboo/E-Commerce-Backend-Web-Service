package org.ace.hcl.orderbillingsystem.inventoryservice.controllers;

import org.ace.hcl.orderbillingsystem.inventoryservice.entities.Catalogue;
import org.ace.hcl.orderbillingsystem.inventoryservice.entities.Product;
import org.ace.hcl.orderbillingsystem.inventoryservice.model.CatalogueInfo;
import org.ace.hcl.orderbillingsystem.inventoryservice.model.ProductsInfo;
import org.ace.hcl.orderbillingsystem.inventoryservice.services.CatalogueService;
import org.ace.hcl.orderbillingsystem.inventoryservice.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class InventoryController {

    @Autowired
    CatalogueService catalogueService;
    @Autowired
    ProductService productService;

    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    @GetMapping("/catalogues")
    public ResponseEntity<List<CatalogueInfo>> listProductsInCatalogue() {
        log.info("Retrieve Catalogues and products");
        return catalogueService.listProductsInCatalogue();
    }

    @PostMapping("/adjust-inventory")
    public ResponseEntity<Map<Long, String>> adjustInventory(@RequestBody List<ProductsInfo> productList){
        log.info("Request to adjust the inventory before user makes a purchase:"+productList);
        return productService.updateInventory(productList);
    }


}
