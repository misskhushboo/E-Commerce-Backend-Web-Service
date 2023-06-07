package org.ace.hcl.orderbillingsystem.inventoryservice.services;

import org.ace.hcl.orderbillingsystem.inventoryservice.entities.Product;
import org.ace.hcl.orderbillingsystem.inventoryservice.model.ProductsInfo;
import org.ace.hcl.orderbillingsystem.inventoryservice.repositories.ProductsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    ProductsRepository productsRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    public Optional<List<Product>> getProductsByProductsId(List<Long> productList) {
       return productsRepository.findProductByProductList(productList);
    }

    public void save(Product product) {
        productsRepository.save(product);
    }

    public Optional<Product> verifyProducts(String name){

        return productsRepository.findByProductName(name);
    }

    public ResponseEntity<Map<Long, String>> updateInventory(List<ProductsInfo> productList) {

        Map<Long, String> productOrderStatus=new HashMap<>();
        if(productList.size()==0) {
            productOrderStatus.put((long)0,"No products ordered");
            return new ResponseEntity<>(productOrderStatus, HttpStatus.OK);
        }

        for(ProductsInfo productsInfo: productList){
            long productId=productsInfo.getProductId();
            int quantity=productsInfo.getQuantity();
            if(quantity<=0) {
                productOrderStatus.put(productId, "Insufficient quantity for a product");
            }
            else {
                Optional<Product> optProduct = productsRepository.findById(productId);
                if (optProduct.isPresent()) {
                    Product product = optProduct.get();
                    int productQuantity = product.getQuantity();

                    if ((productQuantity - quantity) < 0) {
                        productOrderStatus.put(productId, "Desired quantity unavailable for a product. Product Quantity="+productQuantity+" Desired="+quantity);
                    }
                    else {
                        product.setQuantity(productQuantity - quantity);
                        Product productsaved = (Product) productsRepository.save(product);
                        productOrderStatus.put(productId, "Order Placed");

                        if (productsaved.getQuantity() < 0) {
                            productOrderStatus.put(productId, "Some colission. Quantity ordered by user=" + quantity + " while product quantity dropped below 0");
                        }
                    }
                }
            }
        }
        return new ResponseEntity<>(productOrderStatus, HttpStatus.OK);
    }

    public ResponseEntity<String> rollBackInventory(List<LinkedHashMap> productList) {

        for(LinkedHashMap productsInfo: productList){
            int productId=(Integer)productsInfo.get("productId");
            int quantity=(Integer)productsInfo.get("quantity");

                Optional<Product> optProduct = productsRepository.findById((long)productId);
                if (optProduct.isPresent()) {
                    Product product = optProduct.get();
                    int productQuantity = product.getQuantity();

                        product.setQuantity(productQuantity + quantity);
                        Product productsaved = (Product) productsRepository.save(product);
                }
        }
        return new ResponseEntity<>("Inventory rolled back", HttpStatus.OK);
    }
}
