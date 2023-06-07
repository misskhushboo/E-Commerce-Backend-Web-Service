package org.ace.hcl.orderbillingsystem.paymentservice.controller;

import org.ace.hcl.orderbillingsystem.paymentservice.model.OrderDetails;
import org.ace.hcl.orderbillingsystem.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    PaymentService paymentService;

    @PostMapping("/make-payment")
    private ResponseEntity<String> makePayment(@RequestBody OrderDetails orderDetails){

        log.info("Make Payment for orderId:"+orderDetails.getOrderId()+" | status:"+orderDetails.getStatus());

        orderDetails.setStatus("Payment failed");

        if("Payment failed".equals(orderDetails.getStatus())){
            return paymentService.rollBackAllOrders(orderDetails);
        }
        else{
            return new ResponseEntity<>("Payment made successfully.", HttpStatus.OK);
        }
    }
}
