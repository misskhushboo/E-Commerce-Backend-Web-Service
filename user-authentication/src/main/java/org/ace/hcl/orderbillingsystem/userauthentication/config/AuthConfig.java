package org.ace.hcl.orderbillingsystem.userauthentication.config;

import org.ace.hcl.orderbillingsystem.userauthentication.controller.AuthenticationController;
import org.ace.hcl.orderbillingsystem.userauthentication.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {

    private static final Logger log = LoggerFactory.getLogger(AuthConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .requestMatchers(PathRequest.toH2Console()+"/**").permitAll()
                .requestMatchers("/auth/register", "/auth/login", "/auth/validate","/auth/getUserDetails").permitAll()
                .and()
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.info("AuthenticationManager instance retrieval");
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        log.info("Returning userDetailsService instance");
        return new CustomUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        log.info("AuthenticationProvider invoked");
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @EventListener
    public void loginEventListener(AuthenticationSuccessEvent authenticationSuccessEvent){
        UserDetails principal = (UserDetails) authenticationSuccessEvent.getAuthentication().getPrincipal();
        log.info("Following user successfully authenticated:"+principal.getUsername());
    }
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        log.info("onFailure Event: User is not authenticated:"+failures.getException());
    }
}
