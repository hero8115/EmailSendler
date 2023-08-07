package com.example.emailsendler.generation;

import com.example.emailsendler.EnumRole;
import com.example.emailsendler.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTValidation {

       String key = "max_8115_20041607_M_m";
      long expiretime = 300000;


    public   String generateToken(String username){
        String token = Jwts
                .builder()
                .setSubject(username)
//                .claim(role.name(),Role.class)
                .setIssuedAt(new Date())
                .setExpiration(new Date((System.currentTimeMillis())+expiretime))
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
        return token;
    }


    public  boolean validateToken(String authToken){
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        }
        catch (MalformedJwtException exception){
            System.err.println("Invalid JWT token");
        }
        return false;
    }
//
//    public static void main(String[] args) {
//        String a = "3";
//        int a = Double.parseDouble()
//
//    }

    public String parseUsername(String token){
        String username = Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }
}
