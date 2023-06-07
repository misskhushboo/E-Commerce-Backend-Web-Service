package org.ace.hcl.orderbillingsystem.orchestratorservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ace.hcl.orderbillingsystem.orchestratorservice.model.*;
import org.ace.hcl.orderbillingsystem.orchestratorservice.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BaseController {
    @Autowired
    BaseService baseService;
    @Autowired
    AmqpTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @GetMapping("/catalogues")
    private ResponseEntity<Object> getListOfProducts() {
        log.info("Get Catalogues");
        return baseService.getAllCatalogues();
    }

    @PutMapping("/add-to-cart")
    private ResponseEntity<Object> placeOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest request){

        String username=request.getHeader("username");
        log.info("Place Order:"+ orderRequest.getProducts()+" username="+username);
        orderRequest.setUsername(username);
        return baseService.placeOrder(orderRequest);
    }

    @PostMapping("/make-payment")
    private ResponseEntity<Object> paymentStatus(@RequestBody PaymentMethod paymentMethod, HttpServletRequest request){
        long orderId=paymentMethod.getOrderId();
        log.info("Make Payment for orderId:"+orderId+" for username="+request.getHeader("username"));

        return baseService.makePayment(orderId);
    }

    @GetMapping("/order-history")
    private ResponseEntity<Object> getMyOrdersList(HttpServletRequest request){

        String username=request.getHeader("username");
        log.info("Getting orders for user:"+username);
        return baseService.getOrderHistory(username);
    }

    @GetMapping("/testmessage")
    private String getOrdersPlaced(){
        if(rabbitTemplate!=null) {
            rabbitTemplate.convertAndSend("inventorycancellation", "orders.cancelled", "Example text");
            return "success";
        }
        else
            return "message not sent";
    }
//

}
