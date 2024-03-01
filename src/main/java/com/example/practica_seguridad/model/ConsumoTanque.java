package com.example.practica_seguridad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ConsumoTanque {

    private Long idConsumo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaCosumo;
    private Double cantidadConsumo;
    private Double tiempoRiego;
    private Double cantidadRestante;
    private Boolean estadoTanque;
    private Long idTanque;
}
