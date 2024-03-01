package com.example.practica_seguridad.interfaces;

import com.example.practica_seguridad.model.MonitoreoTemperatura;
import com.example.practica_seguridad.model.ZonaRiego;

import java.util.List;

public interface IMonitoreoTemperaturaService {
    MonitoreoTemperatura create(MonitoreoTemperatura monitoreoTemperatura);
    MonitoreoTemperatura update(MonitoreoTemperatura monitoreoTemperatura);
    MonitoreoTemperatura findById(Integer idMonitoreoTemperatura);
    List<MonitoreoTemperatura> findAll();
    List<MonitoreoTemperatura> findAllTemperatura(ZonaRiego zonaRiego);
    void delete(Integer idMonitoreoTemperatura);
}
