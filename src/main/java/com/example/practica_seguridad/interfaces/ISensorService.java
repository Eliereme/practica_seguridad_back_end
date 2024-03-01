package com.example.practica_seguridad.interfaces;

import com.example.practica_seguridad.model.Sensor;

import java.util.List;

public interface ISensorService {
    Sensor create(Sensor sensor);
    Sensor update(Sensor sensor);
    Sensor findById(Integer idSensor);
    List<Sensor> findAll();
    void delete(Integer idSensor);
}
