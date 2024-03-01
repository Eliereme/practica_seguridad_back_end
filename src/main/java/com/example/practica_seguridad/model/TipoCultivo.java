package com.example.practica_seguridad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table(name = "tipoCultivo")
@AllArgsConstructor
public class TipoCultivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoCultivo")
    private Integer idTipoCultivo;
    @Column(name = "tipoCultivo", nullable = false)
    private String tipoCultivo;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    public TipoCultivo() {

    }
}
