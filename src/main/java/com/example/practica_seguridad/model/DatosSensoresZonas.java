package com.example.practica_seguridad.model;

import lombok.Data;

import java.util.List;
@Data
public class DatosSensoresZonas {
    private ZonaRiego zonas;
    private List<DetalleSensor> sensores;
}
