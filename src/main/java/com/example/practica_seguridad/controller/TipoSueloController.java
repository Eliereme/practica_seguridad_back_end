package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.TipoSuelo;
import com.example.practica_seguridad.service.TipoSueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tiposuelo")
public class TipoSueloController {
    @Autowired
    private TipoSueloService tipoSueloService;
    private TipoSuelo tipoSuelo;
    //@Autowired
    //private SimpMessageSendingOperations messagingTemplate;

    @PostMapping("/registro")
    public ResponseEntity<TipoSuelo> registraTipoSuelo(@RequestBody TipoSuelo tipoSueloRequest) {
        try {
            if (tipoSueloRequest == null) {
                return new ResponseEntity<>(new TipoSuelo(-1, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            }
            tipoSuelo = tipoSueloService.create(tipoSueloRequest);
            if (tipoSuelo != null && tipoSuelo.getIdTipoSuelo() > 0) {
                //messagingTemplate.convertAndSend("/topic/tiposueloList", tipoSueloService.findAll());
                return new ResponseEntity<>(tipoSuelo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new TipoSuelo(-1, "Valores ya anteriormente registrados."), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new TipoSuelo(-1, "Campos Incompletos"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new TipoSuelo(-1, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<TipoSuelo> actualizarTipoSuelo(@RequestBody TipoSuelo tipoSueloRequest) {
        try {
            if (tipoSueloRequest == null) {
                return new ResponseEntity<>(new TipoSuelo(-1, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            }
            tipoSuelo = tipoSueloService.update(tipoSueloRequest);
            if (tipoSuelo != null && tipoSuelo.getIdTipoSuelo() > 0) {
                return new ResponseEntity<>(tipoSuelo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new TipoSuelo(-1, "Valores ya anteriormente registrados."), HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(new TipoSuelo(-1, "Campos Incompletos"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new TipoSuelo(-1, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/listatiposuelo")
    public ResponseEntity<List<TipoSuelo>> listaTipoCultivo() {
        try {
            return new ResponseEntity<>(tipoSueloService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TipoSuelo> delete(@PathVariable("id") Integer idTipoSuelo) {
        try {
            tipoSuelo = tipoSueloService.findById(idTipoSuelo);
            if (tipoSuelo == null)
                return new ResponseEntity<>(new TipoSuelo(-1, "Ocurrió un error inesperado."), HttpStatus.CONFLICT);
            tipoSueloService.delete(idTipoSuelo);
            return new ResponseEntity<>(tipoSuelo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TipoSuelo(-1, "Ocurrió un error inesperado."), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/busqueda")
    public ResponseEntity<List<TipoSuelo>> busquedaTipoSuelo(@RequestBody TipoSuelo tipoSueloRequest) {
        try {
            if (tipoSueloRequest == null)
                return new ResponseEntity<>(tipoSueloService.findAll(), HttpStatus.OK);
            else
                return new ResponseEntity<>(tipoSueloService.findByNombre(tipoSueloRequest.getTipoSuelo()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(tipoSueloService.findAll(), HttpStatus.OK);
        }
    }

}
