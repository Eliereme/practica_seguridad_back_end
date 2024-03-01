package com.example.practica_seguridad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Sensor")
@Data
@AllArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSensor")
    private Long idSensor;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fechaInstalacion")
    private Date fechaInstalacion;
    @Column(name = "tipoSensor")
    private String tipoSensor;
    @Column(name = "urlSensor")
    private String urlSensor;

    public Sensor() {

    }

    public Sensor(Long idSensor, String nombre, String descripcion) {
        this.idSensor = idSensor;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
