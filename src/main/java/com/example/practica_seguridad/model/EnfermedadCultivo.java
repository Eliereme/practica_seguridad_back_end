package com.example.practica_seguridad.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EnfermedadCultivo {
    private Cultivo cultivo;
    private List<DetalleEnfermedad>detalleEnfermedades;
}
