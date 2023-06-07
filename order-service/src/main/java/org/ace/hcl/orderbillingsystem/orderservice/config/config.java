package org.ace.hcl.orderbillingsystem.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class config {

    @Bean
    public WebClient orchestratorWebClient(){
        return WebClient.create("http://localhost:9090/orchestrator/");

    }
}
