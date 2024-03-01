package com.example.practica_seguridad.controller;


import com.example.practica_seguridad.model.TipoCultivo;
import com.example.practica_seguridad.service.TipoCultivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tipocultivo")
public class TipoCultivoController {
    @Autowired
    private TipoCultivoService tipoCultivoService;
    private TipoCultivo tipoCultivo;

    @PostMapping("/registro")
    public ResponseEntity<TipoCultivo> registraTipoCultivo(@RequestBody TipoCultivo tipoCultivoRequest) {
        try {
            if (tipoCultivoRequest == null) {
                return new ResponseEntity<>(new TipoCultivo(-1, "Ocurrió un error. Vuelva a intentarlo luego.", "Campos Incompletos"), HttpStatus.BAD_REQUEST);
            }
            tipoCultivo = tipoCultivoService.create(tipoCultivoRequest);
            if (tipoCultivo != null && tipoCultivo.getIdTipoCultivo() > 0) {
                return new ResponseEntity<>(tipoCultivo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new TipoCultivo(-1, tipoCultivoRequest.getTipoCultivo(), "Valores ya anteriormente registrados."), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new TipoCultivo(-1, "Ocurrió un error inesperado.", "Campos Incompletos"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new TipoCultivo(-1, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<TipoCultivo> actulizarTipoCultivo(@RequestBody TipoCultivo tipoCultivoRequest) {
        try {
            if (tipoCultivoRequest == null) {
                return new ResponseEntity<>(new TipoCultivo(-1, "Ocurrió un error. Vuelva a intentarlo luego.", "Campos Incompletos"), HttpStatus.BAD_REQUEST);
            }
            tipoCultivo = tipoCultivoService.update(tipoCultivoRequest);
            if (tipoCultivo != null && tipoCultivo.getIdTipoCultivo() > 0) {
                return new ResponseEntity<>(tipoCultivo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new TipoCultivo(-1, tipoCultivoRequest.getTipoCultivo(), "Valores ya anteriormente registrados."), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new TipoCultivo(-1, "Ocurrió un error inesperado.", "Campos Incompletos"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new TipoCultivo(-1, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listatipocultivo")
    public ResponseEntity<List<TipoCultivo>> listaTipoCultivo() {
        try {
            return new ResponseEntity<>(tipoCultivoService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TipoCultivo> delete(@PathVariable("id") Integer idTipoCultivo) {
        try {
            tipoCultivo = tipoCultivoService.findById(idTipoCultivo);
            if (tipoCultivo == null)
                return new ResponseEntity<>(new TipoCultivo(-1, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.CONFLICT);
            tipoCultivoService.delete(idTipoCultivo);
            return new ResponseEntity<>(tipoCultivo, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new TipoCultivo(-1, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/busqueda")
    public ResponseEntity<List<TipoCultivo>> busquedaTipoCultivo(@RequestBody TipoCultivo tipoCultivoRequest) {
        try {
            if (tipoCultivoRequest == null)
                return new ResponseEntity<>(tipoCultivoService.findAll(), HttpStatus.OK);
            else
                return new ResponseEntity<>(tipoCultivoService.findByNombre(tipoCultivoRequest.getTipoCultivo()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(tipoCultivoService.findAll(), HttpStatus.OK);
        }
    }
}
