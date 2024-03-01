package com.example.practica_seguridad.interfaces;

import com.example.practica_seguridad.model.MonitoreoSuelo;
import com.example.practica_seguridad.model.ZonaRiego;

import java.util.List;

public interface IMonitoreoSueloService {
    MonitoreoSuelo create(MonitoreoSuelo monitoreoSuelo);
    MonitoreoSuelo update(MonitoreoSuelo monitoreoSuelo);
    MonitoreoSuelo findById(Integer idMonitoreoSuelo);
    List<MonitoreoSuelo> findAll();
    List<MonitoreoSuelo> findAllSuelo(ZonaRiego zonaRiego);
    void delete(Integer idMonitoreoSuelo);
}
