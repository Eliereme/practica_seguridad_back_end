package com.example.practica_seguridad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalleSensor")
@Data
public class DetalleSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsSensorDetalle")
    private Integer idsSensorDetalle;
    @ManyToOne
    @JoinColumn(name = "idSensor")
    private Sensor sensor;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idZona")
    private ZonaRiego zona;

    public DetalleSensor(Integer idsSensorDetalle, Sensor sensor, ZonaRiego zona) {
        this.idsSensorDetalle = idsSensorDetalle;
        this.sensor = sensor;
        this.zona = zona;
    }

    public DetalleSensor() {

    }
}
