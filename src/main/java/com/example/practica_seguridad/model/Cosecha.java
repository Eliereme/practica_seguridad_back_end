package com.example.practica_seguridad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "Cosecha")
@Data
@AllArgsConstructor
public class Cosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCosecha")
    private Long idCosecha;
    @Column(name = "descripcion")
    private String descripcion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fechaSiembra")
    private Date fechaSiembra;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fechaCosecha")
    private Date fechaCosecha;
    @Column(name = "rendimiento")
    private double rendimiento;
    @ManyToOne
    @JoinColumn(name = "idZona")
    private ZonaRiego zonaRiego;
    @ManyToOne
    @JoinColumn(name = "idCultivo")
    private Cultivo cultivo;

    public Cosecha() {

    }

    public Cosecha(Long idCosecha, String descripcion) {
        this.idCosecha = idCosecha;
        this.descripcion = descripcion;
    }
}
