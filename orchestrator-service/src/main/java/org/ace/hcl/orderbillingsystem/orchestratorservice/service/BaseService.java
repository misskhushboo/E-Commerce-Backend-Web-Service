package org.ace.hcl.orderbillingsystem.orchestratorservice.service;

import org.ace.hcl.orderbillingsystem.orchestratorservice.networkrequest.HttpRequest;
import org.ace.hcl.orderbillingsystem.orchestratorservice.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Service
public class BaseService {

    private static final Logger log = LoggerFactory.getLogger(BaseService.class);
    @Autowired
    private WebClient inventoryWebClient;
    @Autowired
    private WebClient orderWebClient;
    @Autowired
    private WebClient userAuthWebClient;
    @Autowired
    private WebClient paymentWebClient;

    @Autowired
    AmqpTemplate rabbitTemplate;
    public ResponseEntity<Object> placeOrder(OrderRequest orderRequest){
        log.info("Orchestrator: Sending request to Inventory Microservice to check the inventory.");

        //Update the inventory first
        List<ProductsInfo> productList=orderRequest.getProducts();
        Map<Long,String> inventoryStatusMap= (Map<Long, String>) HttpRequest.makePostRequest("adjust-inventory",inventoryWebClient, Map.class, BodyInserters.fromObject(productList));


        //check inventory
        if(inventoryStatusMap.size()==1 && inventoryStatusMap.entrySet().iterator().next().getKey() == 0  )
            return ResponseWrapper.generateResponse("No products added to place an order ", HttpStatus.BAD_REQUEST, null);

        //create order
        OrderProcessed orderProcessed=(OrderProcessed) HttpRequest.makePostRequest("add-to-cart",orderWebClient, OrderProcessed.class, BodyInserters.fromObject(orderRequest) );
        return ResponseWrapper.generateResponse(orderProcessed.getStatus(), HttpStatus.OK, "OrderId: "+orderProcessed.getOrderId());
        //return ResponseWrapper.generateResponse("OrderId: ",HttpStatus.OK ,inventoryStatusMap);

    }
    public ResponseEntity<Object> getAllCatalogues() {
        log.info("Orchestrator: List all Products In all Catalogues");

        try {
            List<CatalogueInfo> catalogueInfoList=(List<CatalogueInfo>)HttpRequest.makeGetRequest("catalogues",inventoryWebClient,List.class);
            return ResponseWrapper.generateResponse("Request processed", HttpStatus.OK, catalogueInfoList);

        } catch (Exception e) {
            return ResponseWrapper.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    public ResponseEntity<Object> getOrderHistory(String username) {

        List<OrderDetails> orderDetailsList=(List<OrderDetails>) HttpRequest.makeGetRequest("order-history?username="+username,orderWebClient, List.class);

        return ResponseWrapper.generateResponse("Request processed", HttpStatus.OK, orderDetailsList);
    }

    public Object validateToken(String token) throws WebClientResponseException.Forbidden{

        String validateTokenMessage=(String) HttpRequest.makeGetRequest("validate?token="+token,userAuthWebClient, String.class);
        return validateTokenMessage;

    }

    public Object getUserDetailsFromToken(String token) throws WebClientResponseException.Forbidden{

        String userDetailsFromToken=(String) HttpRequest.makeGetRequest("getUserDetails?token="+token,userAuthWebClient, String.class);
        return userDetailsFromToken;

    }

    public ResponseEntity<Object> makePayment(long orderId) {

        OrderDetails orderDetails=(OrderDetails) HttpRequest.makeGetRequest("orders/"+orderId,orderWebClient, OrderDetails.class);
        log.info("Got the details of the order. Number of products: "+orderDetails.getProducts().size());

        String message=(String) HttpRequest.makePostRequest("make-payment",paymentWebClient,String.class,BodyInserters.fromObject(orderDetails));
        return ResponseWrapper.generateResponse(message, HttpStatus.OK, "ok");

    }
}
