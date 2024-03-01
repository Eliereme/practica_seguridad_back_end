package com.example.practica_seguridad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "Notificacion")
@AllArgsConstructor
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNotificacion")
    private Long idNotificacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date fecha;
    private String mensaje;
    private boolean visto;
    private String sensorNotificacion;
    @ManyToOne
    @JoinColumn(name = "idZona")
    private ZonaRiego zonaRiego;

    public Notificacion() {

    }

    public Notificacion(Long idNotificacion, String mensaje) {
        this.idNotificacion = idNotificacion;
        this.mensaje = mensaje;
    }
}
