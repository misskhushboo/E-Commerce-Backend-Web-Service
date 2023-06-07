package org.ace.hcl.orderbillingsystem.orchestratorservice.model;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
public class ResponseWrapper {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", message);
            map.put("status", status.value());
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
    }

    public static Object generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());

        return map;
    }
}
