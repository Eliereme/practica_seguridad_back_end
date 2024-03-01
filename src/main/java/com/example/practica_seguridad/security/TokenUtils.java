package com.example.practica_seguridad.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    private final static String AccesTokenSecret="jbjbsvzbdk346vx345czjxcbvkjbcjsd57d78gdbFESHtyf67yuTyrdGC";
    private final static Long AccesTokenValiditySecons=2_592_000L;
    public static String createToken(String nombre, String email){
        Long expiracionTime=AccesTokenValiditySecons*1000;
        Date expirationDate= new Date(System.currentTimeMillis()+expiracionTime);
        Map<String,Object>extra= new HashMap<>();
        extra.put("nombre",nombre);
        return Jwts.builder().setSubject(email).setExpiration(expirationDate).addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(AccesTokenSecret.getBytes()))
                .compact();
    }
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims= Jwts.parserBuilder()
                    .setSigningKey(AccesTokenSecret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String email = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(email,null, Collections.emptyList());
        }catch (JwtException e){
            return null;
        }
    }
}
