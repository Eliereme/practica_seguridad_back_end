package com.example.practica_seguridad.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials= new AuthCredentials();
        try {
            authCredentials= new ObjectMapper().readValue(request.getReader(),AuthCredentials.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken usernamePat = new UsernamePasswordAuthenticationToken(authCredentials.getCorreo(),
                authCredentials.getPassword(),
                Collections.emptyList());


        return getAuthenticationManager().authenticate(usernamePat);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl detailsImplr=(UserDetailsImpl)authResult.getPrincipal();
        String token= TokenUtils.createToken(detailsImplr.getNombre(),detailsImplr.getUsername());
        response.addHeader("Authorization","Bearer "+token);
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
