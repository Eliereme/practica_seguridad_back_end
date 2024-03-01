package com.example.practica_seguridad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "tipoSuelo")
@Data
@AllArgsConstructor
public class TipoSuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoSuelo")
    private Integer idTipoSuelo;
    @Column(name = "tipoSuelo", nullable = false)
    private String tipoSuelo;
    public TipoSuelo() {

    }
}
