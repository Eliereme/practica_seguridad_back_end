package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.Enfermedad;
import com.example.practica_seguridad.service.EnfermedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/enfermedad")
public class EnfermedadController {
    @Autowired
    private EnfermedadService enfermedadService;
    private Enfermedad enfermedad;

    @PostMapping("/registro")
    public ResponseEntity<Enfermedad> registraEnfermedad(@RequestBody Enfermedad enfermedadRequest) {
        try {
            if (enfermedadRequest == null) {
                return new ResponseEntity<>(new Enfermedad(-1, "Ocurrió un error. Vuelva a intentarlo luego.", "Campos Incompletos", "", "", "", ""), HttpStatus.BAD_REQUEST);
            }
            enfermedad = enfermedadService.create(enfermedadRequest);
            if (enfermedad != null && enfermedad.getIdEnfermedad() > 0) {
                return new ResponseEntity<>(enfermedad, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Enfermedad(-1, enfermedadRequest.getNombreEnfermedad(), "Valores ya anteriormente registrados.", "", "", "", ""), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new Enfermedad(-1, "Ocurrió un error inesperado.", "Campos Incompletos", "", "", "", ""), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Enfermedad(-1, "Ocurrió un error inesperado.", "Error interno", "", "", "", ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Enfermedad> actualizarEnfermedad(@RequestBody Enfermedad enfermedadRequest) {
        try {
            if (enfermedadRequest == null) {
                return new ResponseEntity<>(new Enfermedad(-1, "Ocurrió un error. Vuelva a intentarlo luego.", "Campos Incompletos", "", "", "", ""), HttpStatus.BAD_REQUEST);
            }
            enfermedad = enfermedadService.update(enfermedadRequest);
            if (enfermedad != null && enfermedad.getIdEnfermedad() > 0) {
                return new ResponseEntity<>(enfermedad, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Enfermedad(-1, enfermedadRequest.getNombreEnfermedad(), "Valores ya anteriormente registrados.", "", "", "", ""), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new Enfermedad(-1, "Ocurrió un error inesperado.", "Campos Incompletos", "", "", "", ""), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Enfermedad(-1, "Ocurrió un error inesperado.", "Error interno", "", "", "", ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listaenfermedades")
    public ResponseEntity<List<Enfermedad>> listaTipoCultivo() {
        try {
            return new ResponseEntity<>(enfermedadService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Enfermedad> delete(@PathVariable("id") Integer idEnfermedad) {
        try {
            enfermedad = enfermedadService.findById(idEnfermedad);
            if (enfermedad == null)
                return new ResponseEntity<>(new Enfermedad(-1, "Ocurrió un error. Vuelva a intentarlo luego.", "Campos Incompletos", "", "", "", ""), HttpStatus.CONFLICT);
            enfermedadService.delete(idEnfermedad);
            return new ResponseEntity<>(enfermedad, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Enfermedad(-1, "Ocurrió un error inesperado.", "Error interno", "", "", "", ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/busqueda")
    public ResponseEntity<List<Enfermedad>> busquedaEnfermedad(@RequestBody Enfermedad enfermedadRequest) {
        try {
            if (enfermedadRequest == null)
                return new ResponseEntity<>(enfermedadService.findAll(), HttpStatus.OK);
            else
                return new ResponseEntity<>(enfermedadService.findByNombre(enfermedadRequest.getNombreEnfermedad()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(enfermedadService.findAll(), HttpStatus.OK);
        }
    }
}
