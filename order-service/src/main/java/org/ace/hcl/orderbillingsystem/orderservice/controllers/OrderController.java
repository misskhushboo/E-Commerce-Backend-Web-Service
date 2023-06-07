package org.ace.hcl.orderbillingsystem.orderservice.controllers;

import org.ace.hcl.orderbillingsystem.orderservice.models.OrderDetails;
import org.ace.hcl.orderbillingsystem.orderservice.models.OrderProcessed;
import org.ace.hcl.orderbillingsystem.orderservice.models.OrderRequest;
import org.ace.hcl.orderbillingsystem.orderservice.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("add-to-cart")
    public ResponseEntity<OrderProcessed> placeOrder(@RequestBody OrderRequest orderRequest){
        log.info("Request for username=" + orderRequest.getUsername()+ " Add to cart:"+orderRequest.getProducts());
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping("order-history")
    public ResponseEntity<List<OrderDetails>> getOrderHistory(@RequestParam("username") String username){
        log.info("Get order history for user:"+username);

        return orderService.getOrderHistory(username);
    }
    @GetMapping("orders/{orderId}")
    public ResponseEntity<OrderDetails> getOrderDetails(@PathVariable("orderId") String orderId){
        log.info("Get orders for:"+orderId);

        return orderService.getOrderDetailsForOrderId(Long.valueOf(orderId));
    }
}
