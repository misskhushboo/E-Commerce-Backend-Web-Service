package org.ace.hcl.orderbillingsystem.userauthentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    String token;
    String username;
    int status;
}
