package org.ace.hcl.orderbillingsystem.orchestratorservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class config {

    @Bean
    public WebClient userAuthWebClient(){
        return WebClient.create("http://localhost:8080/auth/");

    }
    @Bean
    public WebClient inventoryWebClient(){
        return WebClient.create("http://localhost:8081/inventory/");

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
