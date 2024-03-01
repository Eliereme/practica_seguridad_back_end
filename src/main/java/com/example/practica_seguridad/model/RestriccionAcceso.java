package com.example.practica_seguridad.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "restriccionAcceso")
public class RestriccionAcceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRestriccion")
    private Long idRestriccion;
    @Column(name = "nombreModulo")
    private String nombreModulo;
    @Column(name = "tipoUsuario")
    private String tipoUsuario;
    @Column(name = "acceso")
    private boolean acceso;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
}
