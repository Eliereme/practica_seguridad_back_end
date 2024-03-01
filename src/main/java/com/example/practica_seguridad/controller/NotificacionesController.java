package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.*;
import com.example.practica_seguridad.service.NotificacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionesController {
    @Autowired
    private NotificacionesService notificacionesService;

    @PostMapping("/listaNotificaciones")
    public ResponseEntity<List<Notificacion>> listaNotificaciones(@RequestBody ZonaRiego zonaRiego) {
        try {
            return new ResponseEntity<>(notificacionesService.findByZonaRiego(zonaRiego), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<Notificacion> createrNotificacion(@RequestBody Notificacion notificacion) {
        try {
            if (notificacion != null)
                return new ResponseEntity<>(notificacionesService.create(notificacion), HttpStatus.OK);
            else
                return new ResponseEntity<>(new Notificacion(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Notificacion(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Notificacion> updateNotificacion(@RequestBody Notificacion notificacion) {
        try {
            if (notificacion != null)
                return new ResponseEntity<>(notificacionesService.update(notificacion), HttpStatus.OK);
            else
                return new ResponseEntity<>(new Notificacion(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Notificacion(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Notificacion> delete(@PathVariable("id") Integer idNotificacion) throws Exception {
        try {
            Notificacion notificacion = notificacionesService.findById(idNotificacion);
            if (notificacion == null)
                return new ResponseEntity<>(new Notificacion(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.CONFLICT);
            notificacionesService.delete(idNotificacion);
            return new ResponseEntity<>(notificacion, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Notificacion(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/busqueda")
    public ResponseEntity<List<Notificacion>> busquedaNotificaciones(@RequestBody FiltroMonitoreoSuelo filtroMonitoreoSuelo) {
        try {
            if (filtroMonitoreoSuelo == null)
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            else
                return new ResponseEntity<>(notificacionesService.findByFecha(filtroMonitoreoSuelo.getZonaRiego(), filtroMonitoreoSuelo.getFechaMedicion()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
}
