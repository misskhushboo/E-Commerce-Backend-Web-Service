package org.ace.hcl.orderbillingsystem.userauthentication.service;

import org.ace.hcl.orderbillingsystem.userauthentication.entity.UserCredential;
import org.ace.hcl.orderbillingsystem.userauthentication.model.CustomUserDetails;
import org.ace.hcl.orderbillingsystem.userauthentication.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialsRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //check whether user with the said name exists in DB or not
        Optional<UserCredential> credential = repository.findByUsername(username);

        //Now convert the Entity (UserCredentials) into UserDetails instance type required by Spring security. So we need a DTO here, lets say, CustomUserDetails.
        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
    }
}
