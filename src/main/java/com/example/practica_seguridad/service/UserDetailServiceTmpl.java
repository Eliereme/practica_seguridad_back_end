package com.example.practica_seguridad.service;

import com.example.practica_seguridad.model.Usuario;
import com.example.practica_seguridad.repository.UsuarioRepository;
import com.example.practica_seguridad.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceTmpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario=usuarioRepository.findOneByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario con el email "+ email+" no existe."));
        return new UserDetailsImpl(usuario);
    }
}
