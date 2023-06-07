package org.ace.hcl.orderbillingsystem.orchestratorservice.networkrequest;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class HttpRequest {

    public static Object makePostRequest(String uri, WebClient webClient, Class class1, BodyInserter bodyInserter) {

        try {
            return webClient.post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bodyInserter)
                    .retrieve()
                    .bodyToMono(class1)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object makePostRequest(String uri, WebClient webClient, Class class1, String queryparameter) {

        try {
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(class1)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object makeGetRequest(String uri, WebClient webClient, Class class1) {

        try {
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(class1)
                    .block();
        } catch (WebClientResponseException.Forbidden e){
            throw e;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
