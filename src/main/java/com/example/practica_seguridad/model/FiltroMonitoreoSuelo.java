package com.example.practica_seguridad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class FiltroMonitoreoSuelo {
    private ZonaRiego zonaRiego;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date fechaMedicion;
}
