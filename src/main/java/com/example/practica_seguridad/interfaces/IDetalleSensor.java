package com.example.practica_seguridad.interfaces;

import com.example.practica_seguridad.model.DetalleSensor;
import com.example.practica_seguridad.model.SensorZona;

import java.util.List;

public interface IDetalleSensor {
    DetalleSensor create(SensorZona detalleSensor);
    List<DetalleSensor> saveAll(List<DetalleSensor> detalleSensors);
    DetalleSensor update(DetalleSensor detalleSensor);
    DetalleSensor findById(Integer idDetalleSensor);
    List<DetalleSensor> findAll();
    void delete(Integer idDetalleSensor);
}
