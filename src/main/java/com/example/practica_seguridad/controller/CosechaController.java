package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.Cosecha;
import com.example.practica_seguridad.model.Cultivo;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.service.CosechaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cosecha")
public class CosechaController {
    @Autowired
    private CosechaService cosechaService;
    private Cosecha cosecha;

    @GetMapping("cosechas")
    public ResponseEntity<List<Cosecha>> listaCosecha() {
        try {
            return new ResponseEntity<>(cosechaService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<Cosecha> createrCosecha(@RequestBody Cosecha cosechaRequest) {
        try {
            if (cosechaRequest == null) {
                return new ResponseEntity<>(new Cosecha(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            }
            cosecha = cosechaService.create(cosechaRequest);
            if (cosecha != null && cosecha.getIdCosecha() > 0) {
                return new ResponseEntity<>(cosecha, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Cosecha(-1L, cosechaRequest.getDescripcion()), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(new Cosecha(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Cosecha> updateUser(@RequestBody Cosecha cosechaRequest) {
        try {
            if (cosechaRequest == null) {
                return new ResponseEntity<>(new Cosecha(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            }
            cosecha = cosechaService.update(cosechaRequest);
            if (cosecha != null && cosecha.getIdCosecha() > 0) {
                return new ResponseEntity<>(cosecha, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Cosecha(-1L, cosechaRequest.getDescripcion()), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(new Cosecha(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cosecha> delete(@PathVariable("id") Integer idCosecha) throws Exception {
        try {
            cosecha = cosechaService.findById(idCosecha);
            if (cosecha == null)
                return new ResponseEntity<>(new Cosecha(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            cosechaService.delete(idCosecha);
            return new ResponseEntity<>(cosecha, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Cosecha(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/busqueda")
    public ResponseEntity<List<Cosecha>> busquedaCosecha(@RequestBody Cosecha cosechaRequest) {
        try {
            if (cosechaRequest == null)
                return new ResponseEntity<>(cosechaService.findAll(), HttpStatus.OK);
            else
                return new ResponseEntity<>(cosechaService.findByNombre(cosechaRequest.getDescripcion()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(cosechaService.findAll(), HttpStatus.OK);
        }
    }

    @PostMapping("/busquedazona")
    public ResponseEntity<List<Cosecha>> busquedaZonaRiego(@RequestBody ZonaRiego zonaRequest) {
        try {
            if (zonaRequest == null)
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            else
                return new ResponseEntity<>(cosechaService.findByCosecha(zonaRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/busquedacultivo")
    public ResponseEntity<List<Cosecha>> busquedaCultivo(@RequestBody Cultivo cultivo) {
        try {
            if (cultivo == null)
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            else
                return new ResponseEntity<>(cosechaService.findByCultivo(cultivo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
}
