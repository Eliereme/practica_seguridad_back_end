package com.example.practica_seguridad.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalleEnfermedad")
@Data
public class DetalleEnfermedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalleEnfermedad")
    private Integer idDetalleEnfermedad;
    @ManyToOne
    @JoinColumn(name = "idEnfermedad")
    private Enfermedad enfermedad;
    @ManyToOne
    @JoinColumn(name = "idCultivo")
    private Cultivo cultivo;

}
