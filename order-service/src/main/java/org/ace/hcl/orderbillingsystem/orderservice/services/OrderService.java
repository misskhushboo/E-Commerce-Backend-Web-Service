package org.ace.hcl.orderbillingsystem.orderservice.services;

import org.ace.hcl.orderbillingsystem.orderservice.entities.Order;
import org.ace.hcl.orderbillingsystem.orderservice.entities.Product;
import org.ace.hcl.orderbillingsystem.orderservice.models.*;
import org.ace.hcl.orderbillingsystem.orderservice.repositories.OrderRepository;
import org.ace.hcl.orderbillingsystem.orderservice.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private WebClient orchestratorWebClient;
    public ResponseEntity<OrderProcessed> placeOrder(OrderRequest orderRequest){

        List<ProductsInfo> productList= orderRequest.getProducts();
        if(productList.size()==0)
            return new ResponseEntity<OrderProcessed>(new OrderProcessed(-1, "No products to order."), HttpStatus.OK);

        //save list of products info in 1 order for 1 user.
        Order order=new Order(orderRequest.getUsername());
        order.setStatus("Order Placed");
        orderRepository.save(order); //so that Order instance can be saved as part of Product in DB.

        for(ProductsInfo productsInfo: productList){
            Product product=new Product(productsInfo, order);
            productRepository.save(product);
        }

        OrderProcessed orderProcessed=new OrderProcessed();
        orderProcessed.setStatus("Success");
        orderProcessed.setOrderId(order.getOrderId());
        log.info("Order placed successfully. OrderId="+order.getOrderId()+" |username="+orderRequest.getUsername()+" | Product list="+order.getProducts());
        return new ResponseEntity<OrderProcessed>(orderProcessed, HttpStatus.OK);
    }

    public ResponseEntity<List<OrderDetails>> getOrderHistory(String username){
        log.info("Getting all orders for:"+username);
        Optional<List<Order>> optOrderList=orderRepository.findByUsername(username);
        List<Order> orderList=null;
        List<OrderDetails> orderDetailsList=new ArrayList<>();

        if(optOrderList.isPresent()) {
            orderList = optOrderList.get();
            log.info("Following orders found for user:"+username+"- "+orderList);

            for(Order order:orderList){
                OrderDetails orderDetails=new OrderDetails();
                orderDetails.setOrderId(order.getOrderId());
                orderDetails.setStatus(order.getStatus());

                List<ProductsInfo> productsInfoList=new ArrayList<>();
                List<Product> productList=order.getProducts();
                productList.forEach(product-> { productsInfoList.add(new ProductsInfo(product));});
                orderDetails.setProducts(productsInfoList);
                log.info("OrderId="+order.getOrderId()+" Status="+order.getStatus()+" List of Products="+productsInfoList);

                orderDetailsList.add(orderDetails);
            }
        }
        else{
            orderDetailsList.add(new OrderDetails(-1,null, "No Order details found"));
        }
        return new ResponseEntity<>(orderDetailsList, HttpStatus.OK);

    }

    //Not used yet
    public ResponseEntity<OrderDetails> getOrderDetailsForOrderId(Long orderId){

        //
        Optional<Order> optOrder=orderRepository.findByOrderId(orderId);

        Order order=null;
        OrderDetails orderDetails=new OrderDetails();
        log.info("Order found?:"+optOrder.isPresent());

        if(optOrder.isPresent()) {
            order = optOrder.get();
            log.info("Order found for orderId:"+orderId+" for userId:"+order.getUsername()+" status="+order.getStatus());

            orderDetails.setOrderId(order.getOrderId());
            orderDetails.setStatus(order.getStatus());
           // orderDetails.setUsername(order.getUsername());

            List<ProductsInfo> productsInfoList=new ArrayList<>();
            List<Product> productList=order.getProducts();
            productList.forEach(product-> { productsInfoList.add(new ProductsInfo(product));});
            orderDetails.setProducts(productsInfoList);
        }
        else{
            orderDetails.setOrderId(-1);
            orderDetails.setStatus("No order details found");
        }
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);

    }

}
