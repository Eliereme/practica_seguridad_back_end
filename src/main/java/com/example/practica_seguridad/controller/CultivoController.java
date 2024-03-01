package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.Cultivo;
import com.example.practica_seguridad.model.Enfermedad;
import com.example.practica_seguridad.model.EnfermedadCultivo;
import com.example.practica_seguridad.service.CultivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cultivo")
public class CultivoController {
    @Autowired
    private CultivoService cultivoService;
    private Cultivo cultivo;

    @PostMapping("/registro")
    public ResponseEntity<Cultivo> registraCultivo(@RequestBody EnfermedadCultivo cultivoRequest) {
        try {
            if (cultivoRequest == null) {
                return new ResponseEntity<>(new Cultivo(-1, "Ocurrió un error. Vuelva a intentarlo luego.", "Campos Incompletos"), HttpStatus.BAD_REQUEST);
            }
            cultivo = cultivoService.create(cultivoRequest);
            if (cultivo != null && cultivo.getIdCultivo() > 0) {
                return new ResponseEntity<>(cultivo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Cultivo(-1, cultivoRequest.getCultivo().getNombreCultivo(), "Valores ya anteriormente registrados."), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new Cultivo(-1, "Ocurrió un error inesperado.", "Campos Incompletos"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Cultivo(-1, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Cultivo> actulizarCultivo(@RequestBody EnfermedadCultivo cultivoRequest) {
        try {
            if (cultivoRequest == null) {
                return new ResponseEntity<>(new Cultivo(-1, "Ocurrió un error. Vuelva a intentarlo luego.", "Campos Incompletos"), HttpStatus.BAD_REQUEST);
            }
            cultivo = cultivoService.update(cultivoRequest);
            if (cultivo != null && cultivo.getIdCultivo() > 0) {
                return new ResponseEntity<>(cultivo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Cultivo(-1, cultivoRequest.getCultivo().getNombreCultivo(), "Valores ya anteriormente registrados."), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new Cultivo(-1, "Ocurrió un error inesperado.", "Campos Incompletos"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Cultivo(-1, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listacultivo")
    public ResponseEntity<List<Cultivo>> listaCultivo() {
        try {
            return new ResponseEntity<>(cultivoService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/listaenfermedadcultivo")
    public ResponseEntity<List<Enfermedad>> listaEnfermedad(@RequestBody Cultivo cultivo) {
        try {
            return new ResponseEntity<>(cultivoService.findListaEnfermedades(cultivo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cultivo> delete(@PathVariable("id") Integer idTipoCultivo) {
        try {
            cultivo = cultivoService.findById(idTipoCultivo);
            if (cultivo == null)
                return new ResponseEntity<>(new Cultivo(-1, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.CONFLICT);
            cultivoService.delete(idTipoCultivo);
            return new ResponseEntity<>(cultivo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Cultivo(-1, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/busqueda")
    public ResponseEntity<List<Cultivo>> busquedaCultivo(@RequestBody Cultivo cultivoRequest) {
        try {
            if (cultivoRequest == null)
                return new ResponseEntity<>(cultivoService.findAll(), HttpStatus.OK);
            else
                return new ResponseEntity<>(cultivoService.findByNombre(cultivoRequest.getNombreCultivo()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(cultivoService.findAll(), HttpStatus.OK);
        }
    }
}
