package com.example.practica_seguridad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Table(name = "monitoreoTemperatura")
@Data
public class MonitoreoTemperatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTemperatura")
    private Long idTemperatura;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "fechaMedicion", nullable = false)
    private Date fechaMedicion;
    @Column(name = "temperatura")
    private double temperatura;
    @Column(name = "humedad")
    private double humedad;
    @ManyToOne
    @JoinColumn(name = "idZona")
    private ZonaRiego zonaRiego;

    public MonitoreoTemperatura() {

    }

    public MonitoreoTemperatura(Long idTemperatura) {
        this.idTemperatura = idTemperatura;
    }

    public MonitoreoTemperatura(Long idTemperatura, Date fechaMedicion, double temperatura, double humedad, Long zonaRiego) {
        this.zonaRiego = new ZonaRiego();
        this.idTemperatura = idTemperatura;
        this.fechaMedicion = fechaMedicion;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.zonaRiego.setIdZona(zonaRiego);
    }

    public MonitoreoTemperatura(Long idTemperatura, Date fechaMedicion, double temperatura, double humedad, ZonaRiego zonaRiego) {
        this.idTemperatura = idTemperatura;
        this.fechaMedicion = fechaMedicion;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.zonaRiego = zonaRiego;
    }
}
