package org.ace.hcl.orderbillingsystem.inventoryservice.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ace.hcl.orderbillingsystem.inventoryservice.model.ProductsInfo;
import org.ace.hcl.orderbillingsystem.inventoryservice.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class AwsSubscriber {

    private static final Logger log = LoggerFactory.getLogger(AwsSubscriber.class);
    @Autowired
    AmqpTemplate rabbitTemplate;

    @Autowired
    ProductService productService;
    @RabbitListener(queues = "InventoryQueue")
    public  void receiveMessage(@Payload String message) {
        List<LinkedHashMap> jsonData=null;
        try {
            jsonData = (List<LinkedHashMap>)new ObjectMapper().readValue(message,List.class);

            log.info("RabbitMQ Subscriber receiving messages:"+jsonData.size()+"|"+jsonData);

            productService.rollBackInventory(jsonData);

        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
    }

}
