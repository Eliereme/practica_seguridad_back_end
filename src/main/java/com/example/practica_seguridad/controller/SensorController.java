package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.DetalleSensor;
import com.example.practica_seguridad.model.Sensor;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/Sensor")
public class SensorController {
    @Autowired
    private SensorService sensorService;
    private Sensor sensor;

    @PostMapping("/registro")
    public ResponseEntity<Sensor> registraSensor(@RequestBody Sensor sensorRequest) {
        try {
            if (sensorRequest == null) {
                return new ResponseEntity<>(new Sensor(-1L, "Ocurrió un error. Vuelva a intentarlo luego.", "Campos Incompletos"), HttpStatus.BAD_REQUEST);
            }
            sensor = sensorService.create(sensorRequest);
            if (sensor != null && sensor.getIdSensor() > 0) {
                return new ResponseEntity<>(sensor, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Sensor(-1L, sensorRequest.getNombre(), "Valores ya anteriormente registrados."), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new Sensor(-1L, "Ocurrió un error inesperado.", "Campos Incompletos"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Sensor(-1L, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Sensor> actulizarSensor(@RequestBody Sensor sensorRequest) {
        try {
            if (sensorRequest == null) {
                return new ResponseEntity<>(new Sensor(-1L, "Ocurrió un error. Vuelva a intentarlo luego.", "Campos Incompletos"), HttpStatus.BAD_REQUEST);
            }
            sensor = sensorService.update(sensorRequest);
            if (sensor != null && sensor.getIdSensor() > 0) {
                return new ResponseEntity<>(sensor, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Sensor(-1L, sensorRequest.getNombre(), "Valores ya anteriormente registrados."), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new Sensor(-1L, "Ocurrió un error inesperado.", "Campos Incompletos"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Sensor(-1L, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listasensor")
    public ResponseEntity<List<Sensor>> listaSensor() {
        try {
            return new ResponseEntity<>(sensorService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Sensor> delete(@PathVariable("id") Integer idTipoCultivo) {
        try {
            sensor = sensorService.findById(idTipoCultivo);
            if (sensor == null)
                return new ResponseEntity<>(new Sensor(-1L, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.CONFLICT);
            sensorService.delete(idTipoCultivo);
            return new ResponseEntity<>(sensor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Sensor(-1L, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/busqueda")
    public ResponseEntity<List<Sensor>> busquedaSensor(@RequestBody Sensor sensorRequest) {
        try {
            if (sensorRequest == null)
                return new ResponseEntity<>(sensorService.findAll(), HttpStatus.OK);
            else
                return new ResponseEntity<>(sensorService.findByNombre(sensorRequest.getNombre()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(sensorService.findAll(), HttpStatus.OK);
        }
    }
    @PostMapping("/busquedazonariego")
    public ResponseEntity<List<DetalleSensor>> busquedaSensorZonaRiego(@RequestBody ZonaRiego zonaRiego) {
        try {
            if (zonaRiego == null)
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            else
                return new ResponseEntity<>(sensorService.findByZonaRiego(zonaRiego), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

}
