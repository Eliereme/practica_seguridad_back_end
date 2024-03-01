package com.example.practica_seguridad.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ChartDataSuelo {
    private List<String> labels;
    private List<Double> dataFosforo;
    private List<Double> dataPotasio;
    private List<Double> dataNitrogeno;
    private List<Double> dataHumedadSuelo;

    public ChartDataSuelo() {
    }
}
