package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.DepositoAgua;
import com.example.practica_seguridad.model.SistemaRiego;
import com.example.practica_seguridad.model.Usuario;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.security.TokenUtils;
import com.example.practica_seguridad.service.ArduinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sistema")
public class ArduinoController {

    @Autowired
    private ArduinoService arduinoService;
    private SistemaRiego sistemaRiego;

    @PostMapping
    public String verificarSistema(@RequestBody ZonaRiego zonaRiego) {
        if (arduinoService.findByName(zonaRiego.getSistemaRiego().getNombre()) != null) {
            return TokenUtils.createToken(zonaRiego.getSistemaRiego().getNombre(), String.valueOf(zonaRiego.getSistemaRiego().getTipo()));
        } else {
            return "No se logro verificar el sensor";
        }
    }
    @PostMapping("/verificaciontanque")
    public String verificarTanque(@RequestBody DepositoAgua depositoAgua) {
        if (arduinoService.findByName(depositoAgua.getZonaRiego().getSistemaRiego().getNombre()) != null) {
            return TokenUtils.createToken(depositoAgua.getZonaRiego().getSistemaRiego().getNombre(), String.valueOf(depositoAgua.getZonaRiego().getSistemaRiego().getTipo()));
        } else {
            return "No se logro verificar el sensor";
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<SistemaRiego> registraSistema(@RequestBody SistemaRiego sistemaRiegoRequest) {
        try {
            if (sistemaRiegoRequest == null) {
                return new ResponseEntity<>(new SistemaRiego(-1L, "Ocurrió un error. Vuelva a intentarlo luego.", "Campos Incompletos"), HttpStatus.BAD_REQUEST);
            }
            sistemaRiego = arduinoService.create(sistemaRiegoRequest);
            if (sistemaRiego != null && sistemaRiego.getIdSistema() > 0) {
                return new ResponseEntity<>(sistemaRiego, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new SistemaRiego(-1L, sistemaRiegoRequest.getNombre(), "Valores ya anteriormente registrados."), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new SistemaRiego(-1L, "Ocurrió un error inesperado.", "Campos Incompletos"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new SistemaRiego(-1L, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<SistemaRiego> actulizarTipoCultivo(@RequestBody SistemaRiego sistemaRiegoRequest) {
        try {
            if (sistemaRiegoRequest == null) {
                return new ResponseEntity<>(new SistemaRiego(-1L, "Ocurrió un error. Vuelva a intentarlo luego.", "Campos Incompletos"), HttpStatus.BAD_REQUEST);
            }
            sistemaRiego = arduinoService.update(sistemaRiegoRequest);
            if (sistemaRiego != null && sistemaRiego.getIdSistema() > 0) {
                return new ResponseEntity<>(sistemaRiego, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new SistemaRiego(-1L, sistemaRiegoRequest.getNombre(), "Valores ya anteriormente registrados."), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new SistemaRiego(-1L, "Ocurrió un error inesperado.", "Campos Incompletos"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new SistemaRiego(-1L, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listasistema")
    public ResponseEntity<List<SistemaRiego>> listaTipoCultivo() {
        try {
            return new ResponseEntity<>(arduinoService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/listaistemasuser")
    public ResponseEntity<List<SistemaRiego>> listaSistema(@RequestBody Usuario usuario) {
        try {
            return new ResponseEntity<>(arduinoService.findByUsuario(usuario), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SistemaRiego> delete(@PathVariable("id") Integer idSistemaRiego) {
        try {
            sistemaRiego = arduinoService.findById(idSistemaRiego);
            if (sistemaRiego == null)
                return new ResponseEntity<>(new SistemaRiego(-1L, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.CONFLICT);
            arduinoService.delete(idSistemaRiego);
            return new ResponseEntity<>(sistemaRiego, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new SistemaRiego(-1L, "Ocurrió un error inesperado.", "Error interno"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/busqueda")
    public ResponseEntity<List<SistemaRiego>> busquedaSistemaRiego(@RequestBody SistemaRiego sistemaRiegoRequest) {
        try {
            if (sistemaRiegoRequest == null)
                return new ResponseEntity<>(arduinoService.findAll(), HttpStatus.OK);
            else
                return new ResponseEntity<>(arduinoService.findByNombre(sistemaRiegoRequest.getNombre()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(arduinoService.findAll(), HttpStatus.OK);
        }
    }


}
