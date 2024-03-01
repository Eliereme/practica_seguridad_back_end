package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.*;
import com.example.practica_seguridad.service.DetalleSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/detallesensor")
public class DetalleSensorController {
    @Autowired
    private DetalleSensorService detalleSensorService;
    private DetalleSensor sensor;
    @PostMapping("/datosSensoresZonas")
    public ResponseEntity<DetalleSensor> recibirDatosSensoresZonas(@RequestBody SensorZona datosSensoresZonas) {
        try {
            if (datosSensoresZonas == null && datosSensoresZonas.getIdZona() > 0 && datosSensoresZonas.getSensores().size() > 0) {
                return new ResponseEntity<>(new DetalleSensor(-1, new Sensor(), new ZonaRiego()), HttpStatus.BAD_REQUEST);
            }
            sensor = detalleSensorService.create(datosSensoresZonas);
            if (sensor != null && sensor.getIdsSensorDetalle() > 0) {
                return new ResponseEntity<>(sensor, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new DetalleSensor(-1, new Sensor(), new ZonaRiego()), HttpStatus.BAD_REQUEST);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new DetalleSensor(-1, new Sensor(), new ZonaRiego()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(new DetalleSensor(-1, new Sensor(), new ZonaRiego()), HttpStatus.BAD_REQUEST);
        }
    }
}
