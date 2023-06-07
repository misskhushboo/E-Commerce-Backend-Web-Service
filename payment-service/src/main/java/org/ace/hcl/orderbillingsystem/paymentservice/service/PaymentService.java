package org.ace.hcl.orderbillingsystem.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ace.hcl.orderbillingsystem.paymentservice.controller.PaymentController;
import org.ace.hcl.orderbillingsystem.paymentservice.model.OrderDetails;
import org.ace.hcl.orderbillingsystem.paymentservice.model.ProductsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    AmqpTemplate rabbitTemplate;
    public ResponseEntity<String> rollBackAllOrders(OrderDetails orderDetails) {
        log.info("Making request to rollback inventory under orderId:"+orderDetails.getOrderId());

         if(rabbitTemplate!=null) {

            String jsonData=null;
             try {
                 jsonData = new ObjectMapper().writeValueAsString(orderDetails.getProducts()); //List<ProductsInfo>
             } catch (JsonProcessingException e) {
                 log.info("Exception:"+e.getMessage());
             }
             rabbitTemplate.convertAndSend("inventorycancellation", "orders.cancelled", jsonData);
             return new ResponseEntity<String>("Inventory updated and order request is rolled back", HttpStatus.OK);
        }
         else
            return new ResponseEntity<String>("Could not rollback Inventory. Try again later.", HttpStatus.OK);
    }
}
