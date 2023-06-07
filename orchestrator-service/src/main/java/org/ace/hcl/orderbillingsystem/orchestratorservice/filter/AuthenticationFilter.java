package org.ace.hcl.orderbillingsystem.orchestratorservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ace.hcl.orderbillingsystem.orchestratorservice.exception.CustomExceptionHandler;
import org.ace.hcl.orderbillingsystem.orchestratorservice.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    BaseService baseService;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, RuntimeException {

        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse)servletResponse;

        String headerToken=httpServletRequest.getHeader("token");
        String username=httpServletRequest.getHeader("username");
        log.info("DoFilter API validate token:"+headerToken);

        String validation=null;
        try{
           validation = (String)baseService.validateToken(headerToken);
           log.info("Validation result:"+validation);

        }catch(WebClientResponseException.Forbidden e){
            log.info("Exception thrown:"+e.getMessage());

            Map<String, Object> mapObj = new HashMap<>();
            mapObj.put("message", "Access Forbidden. Please check the Token or login again.");
            mapObj.put("status", 400);

            //set the response object
            httpServletResponse.setStatus((Integer)mapObj.get("status"));
            httpServletResponse.setContentType("application/json");

            ObjectMapper mapper = new ObjectMapper();
            PrintWriter out = httpServletResponse.getWriter();
            out.print(mapper.writeValueAsString(mapObj ));
            out.flush();

            return;
        }

        log.info("DoFilter API validation performed:"+validation);
        if("Token is valid".equals(validation)){
            ((HttpServletResponse) servletResponse).addHeader("username",username);
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else{
            log.info("DoFilter API validation performed:In Else block. Let user login again.");
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("login.html");
            dispatcher.forward(servletRequest, servletResponse);

        }
    }

    @Override    public void destroy() {
        Filter.super.destroy();
    }
}
