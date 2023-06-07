package org.ace.hcl.orderbillingsystem.userauthentication.controller;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.ace.hcl.orderbillingsystem.userauthentication.entity.UserCredential;
import org.ace.hcl.orderbillingsystem.userauthentication.model.AuthRequest;
import org.ace.hcl.orderbillingsystem.userauthentication.service.AuthenticatioService;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticatioService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        log.info("Registering new user="+user.getUsername()+" "+user.getPassword()+" "+user.getEmail());
        return "UserId saved:"+service.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAttempt(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        log.info("First Login: "+ authRequest.getUsername());

        /*check if the user is valid and exists in DB, then only generate Token
        It takes help from AuthenticationProvider who inturn takes help from UserDetailsService to perform DB validation*/
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authenticate.isAuthenticated()) {
            log.info("User authenticated. Generating Token..");
            String token=service.generateToken(authRequest.getUsername());
            response.setHeader("username",authRequest.getUsername());
            response.setHeader("token", token);
            return ResponseEntity.ok("User logged In!");

        } else {
            log.info("User not authenticated");
            response.setHeader("username","");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) throws JwtException {
        log.info("Validate Token: "+token);
        return service.validateToken(token);
    }

    @GetMapping("/getUserDetails")
    public String getUserDetailsFromToken(@RequestParam("token") String token){
        log.info("Getting user details from Token");
        return service.getUserDetailsFromToken(token);
    }

}
