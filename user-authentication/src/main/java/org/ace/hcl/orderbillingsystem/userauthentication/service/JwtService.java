package org.ace.hcl.orderbillingsystem.userauthentication.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.ace.hcl.orderbillingsystem.userauthentication.controller.AuthenticationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    public String validateToken(final String token) throws JwtException{
        try {
            log.info("JwtService: Validate Token:"+token);
            Jws<Claims> jwsClaims=Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);

            if(jwsClaims!=null)
                return "Token is valid";
            else
                return "Invalid Token";
        } /*catch (ExpiredJwtException e ) {
            return "Invalid Token: ExpiredJwtException ("+e.getMessage()+")";
        } catch (UnsupportedJwtException e) {
            return "Invalid Token: UnsupportedJwtException ("+e.getMessage()+")";
        } catch (MalformedJwtException e) {
            return "Invalid Token: MalformedJwtException"+e.getMessage()+")";
        } catch (SignatureException e) {
            return "Invalid Token: SignatureException"+e.getMessage()+")";
        } catch (IllegalArgumentException e) {
            return "Invalid Token: IllegalArgumentException"+e.getMessage()+")";
        }*/catch (JwtException e){
            throw e;
        }
    }


    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        //claims.put("userId",);
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserDetails(String token) {

       // log.info("JwtService: Get user details:");
        Jws<Claims> jwsClaims=Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
       // log.info("JWTService claims: "+jwsClaims.getBody().getId()+" "+jwsClaims.getBody().getIssuer()+ "  "+ jwsClaims.getBody().getAudience()+"" + jwsClaims.getBody().getSubject());
        return jwsClaims.getBody().getSubject();
    }
}
