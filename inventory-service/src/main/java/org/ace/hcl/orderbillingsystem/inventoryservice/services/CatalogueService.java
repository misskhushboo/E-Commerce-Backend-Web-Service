package org.ace.hcl.orderbillingsystem.inventoryservice.services;

import org.ace.hcl.orderbillingsystem.inventoryservice.entities.Catalogue;
import org.ace.hcl.orderbillingsystem.inventoryservice.entities.Product;
import org.ace.hcl.orderbillingsystem.inventoryservice.model.CatalogueInfo;
import org.ace.hcl.orderbillingsystem.inventoryservice.model.ProductsInfo;
import org.ace.hcl.orderbillingsystem.inventoryservice.repositories.CatalogueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatalogueService {

    @Autowired
    CatalogueRepository catalogueRepository;
    @Autowired
    ProductService productService;
    private static final Logger log = LoggerFactory.getLogger(CatalogueService.class);
    public void addProductsToCatalogue(ProductsInfo productsInfo) {
        Product product =new Product();
        product.setProductName(productsInfo.getProductName());
        product.setRating(productsInfo.getRating());
        product.setPrice(productsInfo.getPrice());
        product.setDiscount(productsInfo.getDiscount());
        product.setQuantity(productsInfo.getQuantity());

        //Pls verify
        Optional<Catalogue> optionalCatalogue=catalogueRepository.findByCatalogueName(productsInfo.getCatalogueName());
        Catalogue catalogue=optionalCatalogue.isPresent()? optionalCatalogue.get(): new Catalogue();

        //If there's no catalogue while adding products, a new catalogue is created. This should return an error because the catalogueId is not set.
        //Should this send a request to create_catalogue endpoint in the same microservice?

        catalogue.setCatalogueName(productsInfo.getCatalogueName());
        catalogueRepository.save(catalogue);
        product.setCatalogue(catalogue); //catalogueId is not set even when line 64 is executed. Isn't catalogue a live object?

        productService.save(product);

    }

    public ResponseEntity<List<CatalogueInfo>> listProductsInCatalogue() {

       long dbcount= catalogueRepository.count();
       log.info("Total number of catalogues in the database:"+dbcount);

       List<Catalogue> catalogues= catalogueRepository.findAll();
       List<CatalogueInfo> catalogueInfoList=new ArrayList<CatalogueInfo>();

       for(Catalogue catalogue:catalogues){
           CatalogueInfo catalogueInfo=new CatalogueInfo();
           ArrayList<ProductsInfo> productsInfos=new ArrayList<>();

           catalogueInfo.setCatalogueName(catalogue.getCatalogueName());

           List<Product> productList=catalogue.getProducts();
           for(Product prod: productList){
               productsInfos.add(new ProductsInfo(prod, catalogue.getCatalogueName()));
           }
           catalogueInfo.setProductsInfoList(productsInfos);
           catalogueInfoList.add(catalogueInfo);
       }
        return new ResponseEntity<>(catalogueInfoList, HttpStatus.OK);
    }


}
