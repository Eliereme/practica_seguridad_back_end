package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.exeptions.ModelNotFoundException;
import com.example.practica_seguridad.model.*;
import com.example.practica_seguridad.service.InformeConsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/Informe")
public class InformeConsumoController {
    @Autowired
    private InformeConsumoService informeConsumoService;

    @GetMapping("/listainforme")
    public ResponseEntity<List<InformeConsumo>> listaInforme() {
        try {
            return new ResponseEntity<>(informeConsumoService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<InformeConsumo> createrInforme(@RequestBody InformeConsumo informeConsumo) {
        try {
            if (informeConsumo != null)
                return new ResponseEntity<>(informeConsumoService.create(informeConsumo), HttpStatus.OK);
            else
                return new ResponseEntity<>(new InformeConsumo(-1L, 0.0), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(new InformeConsumo(-1L, 0.0), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/informediario")
    public ResponseEntity<List<InformeConsumo>> informediario(@RequestBody DepositoAgua depositoAgua) {
        try {
            if (depositoAgua != null)
                return new ResponseEntity<>(informeConsumoService.findByFecha(depositoAgua, depositoAgua.getFecha()), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/consumo")
    public ResponseEntity<Boolean> createrInformeConsumo(@RequestBody ConsumoTanque informeConsumo) {
        try {
            if (informeConsumo != null)
                return new ResponseEntity<>(informeConsumoService.crearInformeCosumo(informeConsumo), HttpStatus.OK);
            else
                return new ResponseEntity<>(false, HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<InformeConsumo> updateInforme(@RequestBody InformeConsumo informeConsumo) {
        return new ResponseEntity<>(informeConsumoService.update(informeConsumo), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InformeConsumo> finById(@PathVariable("id") Integer idInformeConsumo) {
        InformeConsumo informeConsumo = informeConsumoService.findById(idInformeConsumo);
        if (informeConsumo == null)
            throw new ModelNotFoundException("No se encontro una cosecha");
        return new ResponseEntity<>(informeConsumo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer idInformeConsumo) throws Exception {
        InformeConsumo informeConsumo = informeConsumoService.findById(idInformeConsumo);
        if (informeConsumo == null)
            throw new ModelNotFoundException("La cosecha no se puede eliminar");
        informeConsumoService.delete(idInformeConsumo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
