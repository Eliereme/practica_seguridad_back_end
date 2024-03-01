package com.example.practica_seguridad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "datosMeteorologicos")
@Data
public class DatosMeteorologicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDatosM")
    private Long idDatosM;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "temperatura")
    private double temperatura;
    @Column(name = "humedad")
    private double humedad;
    @Column(name = "precipitacion")
    private double precipitacion;
    @Column(name = "viento")
    private double viento;
}
