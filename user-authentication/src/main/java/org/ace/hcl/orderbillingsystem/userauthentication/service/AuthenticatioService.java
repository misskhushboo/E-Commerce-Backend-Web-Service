package org.ace.hcl.orderbillingsystem.userauthentication.service;

import io.jsonwebtoken.JwtException;
import org.ace.hcl.orderbillingsystem.userauthentication.controller.AuthenticationController;
import org.ace.hcl.orderbillingsystem.userauthentication.entity.UserCredential;
import org.ace.hcl.orderbillingsystem.userauthentication.repository.UserCredentialsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatioService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;
    private static final Logger log = LoggerFactory.getLogger(AuthenticatioService.class);
    @Autowired
    private JwtService jwtService;

    //bean defined in AuthConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Integer saveUser(UserCredential userCredential){
        //encrypt the password and then save
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        UserCredential userCredential1=userCredentialsRepository.save(userCredential);
        return userCredential1.getId();
    }


    public String generateToken(String username) {
        String token=jwtService.generateToken(username);
        return token;
    }

    public String validateToken(String token) throws JwtException {
        return jwtService.validateToken(token);
    }

    public String getUserDetailsFromToken(String token) {
        return jwtService.getUserDetails(token);
    }
}
