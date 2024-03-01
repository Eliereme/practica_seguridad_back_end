package com.example.practica_seguridad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "monitoreoCosechaPlaga")
@Data
public class MonitoreoCosechaPlaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMonitoreoPlaga")
    private Integer idMonitoreoPlaga;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "fechaMedicion")
    private Date fechaMedicion;
    @ManyToOne
    @JoinColumn(name = "idZona")
    private ZonaRiego zonaRiego;
}
