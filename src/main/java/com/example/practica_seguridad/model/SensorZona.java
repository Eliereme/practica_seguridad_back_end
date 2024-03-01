package com.example.practica_seguridad.model;

import lombok.Data;

import java.util.List;

@Data
public class SensorZona {
    private int idZona;
    private List<String> sensores;
}
