package com.example.practica_seguridad.security;

import lombok.Data;

@Data
public class AuthCredentials {
    private String correo;
    private String password;
}
