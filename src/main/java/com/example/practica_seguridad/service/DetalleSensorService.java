package com.example.practica_seguridad.service;

import com.example.practica_seguridad.model.DetalleSensor;
import com.example.practica_seguridad.model.Sensor;
import com.example.practica_seguridad.model.SensorZona;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.repository.DetalleSensorRepository;
import com.example.practica_seguridad.interfaces.IDetalleSensor;
import com.example.practica_seguridad.repository.SensorRepository;
import com.example.practica_seguridad.repository.ZonaRiegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DetalleSensorService implements IDetalleSensor {
    @Autowired
    private DetalleSensorRepository detalleSensorRepository;
    @Autowired
    private ZonaRiegoRepository zonaRiegoRepository;
    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public DetalleSensor create(SensorZona detalleSensor) {
        try {
            List<DetalleSensor> detalleSensorLis = new ArrayList<>();
            ZonaRiego zonaRiego = zonaRiegoRepository.findByIdZona(detalleSensor.getIdZona());
            List<DetalleSensor> detalleSensorLisActual = detalleSensorRepository.findByZona(zonaRiego);
            for (String name : detalleSensor.getSensores()) {
                detalleSensorLis.add(new DetalleSensor(-1, sensorRepository.findByNombreIgnoreCase(name), zonaRiego));
            }
            if (detalleSensorLisActual.size() == 0) {
                return detalleSensorRepository.saveAll(detalleSensorLis).get(0);
            }
            for (DetalleSensor sensor : detalleSensorLisActual) {
                detalleSensorLis.removeIf(sensorBusqueda -> Objects.equals(sensor.getSensor().getNombre(), sensorBusqueda.getSensor().getNombre()));
            }
            return detalleSensorLis.size() > 0 ? detalleSensorRepository.saveAll(detalleSensorLis).get(0) : new DetalleSensor(-1, new Sensor(), new ZonaRiego());
        } catch (Exception e) {
            return new DetalleSensor(-1, new Sensor(), new ZonaRiego());
        }
    }

    @Override
    public List<DetalleSensor> saveAll(List<DetalleSensor> detalleSensors) {
        return detalleSensorRepository.saveAll(detalleSensors);
    }

    @Override
    public DetalleSensor update(DetalleSensor detalleSensor) {
        return detalleSensorRepository.save(detalleSensor);
    }

    @Override
    public DetalleSensor findById(Integer idDetalleSensor) {
        Optional<DetalleSensor> detalleSensor = detalleSensorRepository.findById(idDetalleSensor);
        return detalleSensor.orElse(null);
    }

    @Override
    public List<DetalleSensor> findAll() {
        return detalleSensorRepository.findAll();
    }

    @Override
    public void delete(Integer idDetalleSensor) {
        detalleSensorRepository.deleteById(idDetalleSensor);
    }
}
