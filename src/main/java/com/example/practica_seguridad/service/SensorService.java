package com.example.practica_seguridad.service;

import com.example.practica_seguridad.model.DetalleSensor;
import com.example.practica_seguridad.model.Sensor;
import com.example.practica_seguridad.interfaces.ISensorService;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.repository.DetalleSensorRepository;
import com.example.practica_seguridad.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService implements ISensorService {
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private DetalleSensorRepository detalleSensorRepository;

    @Override
    @Transactional
    public Sensor create(Sensor sensor) {
        try {
            List<Sensor> sensorBusqueda=sensorRepository.findByNombreContainingIgnoreCase(sensor.getNombre());
            if (!sensorBusqueda.isEmpty()) {
                return new Sensor(-1L, "", "");
            }
            return sensorRepository.save(sensor);
        } catch (Exception e) {
            return new Sensor(-1L, "", e.getMessage());
        }
    }

    @Override
    @Transactional
    public Sensor update(Sensor sensor) {
        try {
            Sensor existingSensor = sensorRepository.findByNombreIgnoreCase(sensor.getNombre());
            if (existingSensor != null && !existingSensor.getIdSensor().equals(sensor.getIdSensor())) {
                return new Sensor(-1L, "", "");
            }
            return sensorRepository.save(sensor);
        } catch (Exception e) {
            return new Sensor(-1L, "", e.getMessage());
        }
    }

    @Override
    @Transactional
    public Sensor findById(Integer idSensor) {
        try {
            Optional<Sensor> sensor = sensorRepository.findById(idSensor);
            return sensor.orElse(null);
        } catch (Exception e) {
            return new Sensor(-1L, "", e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Sensor> findAll() {
        try {
            return sensorRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idSensor) {
        try {
            sensorRepository.deleteById(idSensor);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public List<Sensor> findByNombre(String nombre) {
        try {
            return sensorRepository.findByNombreContainingIgnoreCase(nombre);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<DetalleSensor> findByZonaRiego(ZonaRiego zonaRiego) {
        try {
            return detalleSensorRepository.findByZona(zonaRiego);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
