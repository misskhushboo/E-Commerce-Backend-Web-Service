package org.ace.hcl.orderbillingsystem.inventoryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class config {

    @Bean
    public WebClient userWebClient(){
        return WebClient.create("http://localhost:8080/user/");

    }

    @Bean
    public WebClient orderWebClient(){
        return WebClient.create("http://localhost:8082/order/");

    }
    @Bean
    public WebClient paymentWebClient(){
        return WebClient.create("http://localhost:8083/payment/");

    }


}
