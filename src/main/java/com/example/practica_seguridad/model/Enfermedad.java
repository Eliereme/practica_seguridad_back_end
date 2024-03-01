package com.example.practica_seguridad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "enfermedad")
@AllArgsConstructor
public class Enfermedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEnfermedad")
    private Integer idEnfermedad;
    @Column(name = "nombreEnfermedad", nullable = false, length = 150)
    private String nombreEnfermedad;
    @Column(name = "descripcion", nullable = false, length = 150)
    private String descripcion;
    @Column(name = "prevencion", nullable = false, length = 150)
    private String prevencion;
    @Column(name = "controlEnfermedad", nullable = false, length = 200)
    private String controlEnfermedad;
    @Column(name = "cicloVida", nullable = false, length = 200)
    private String cicloVida;
    @Column(name = "sintomas", nullable = false, length = 200)
    private String sintomas;


    public Enfermedad() {

    }
}
