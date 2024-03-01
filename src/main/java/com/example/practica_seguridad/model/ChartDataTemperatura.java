package com.example.practica_seguridad.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ChartDataTemperatura {
    private List<String> labels;
    private List<Double> dataHumedad;
    private List<Double> dataTemperatura;

    public ChartDataTemperatura() {
    }
}
