package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.DepositoAgua;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.service.DepositoAguaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/Deposito")
public class DepositoAguaController {
    @Autowired
    private DepositoAguaService depositoAguaService;
    private DepositoAgua depositoAgua;

    @GetMapping("/listadeposito")
    public ResponseEntity<List<DepositoAgua>> listaDeposito() {
        try {
            return new ResponseEntity<>(depositoAguaService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<DepositoAgua> createrDeposito(@RequestBody DepositoAgua depositoAguaRequest) {
        try {
            if (depositoAguaRequest == null) {
                return new ResponseEntity<>(new DepositoAgua(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            }
            depositoAgua = depositoAguaService.create(depositoAguaRequest);
            if (depositoAgua != null && depositoAgua.getIdDeposito() > 0) {
                return new ResponseEntity<>(depositoAgua, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new DepositoAgua(-1L, depositoAguaRequest.getDescripcion()), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(new DepositoAgua(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<DepositoAgua> updateDeposito(@RequestBody DepositoAgua depositoAguaRequest) {
        try {
            if (depositoAguaRequest == null) {
                return new ResponseEntity<>(new DepositoAgua(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            }
            depositoAgua = depositoAguaService.update(depositoAguaRequest);
            if (depositoAgua != null && depositoAgua.getIdDeposito() > 0) {
                return new ResponseEntity<>(depositoAgua, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new DepositoAgua(-1L, depositoAguaRequest.getDescripcion()), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(new DepositoAgua(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepositoAgua> delete(@PathVariable("id") Integer idDepositoAgua) throws Exception {
        try {
            DepositoAgua deposito = depositoAguaService.findById(idDepositoAgua);
            if (deposito == null)
                return new ResponseEntity<>(new DepositoAgua(-1L, "Ocurrió un error inesperado."), HttpStatus.CONFLICT);
            depositoAguaService.delete(idDepositoAgua);
            return new ResponseEntity<>(deposito, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new DepositoAgua(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/busqueda")
    public ResponseEntity<List<DepositoAgua>> busquedaDeposito(@RequestBody DepositoAgua depositoAguaRequest) {
        try {
            if (depositoAguaRequest == null)
                return new ResponseEntity<>(depositoAguaService.findAll(), HttpStatus.OK);
            else
                return new ResponseEntity<>(depositoAguaService.findByDescripcion(depositoAguaRequest.getDescripcion()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(depositoAguaService.findAll(), HttpStatus.OK);
        }
    }
    @PostMapping("/busquedazona")
    public ResponseEntity<List<DepositoAgua>> busquedaDepositoZona(@RequestBody ZonaRiego zonaRiegoRequest) {
        try {
            if (zonaRiegoRequest == null)
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            else
                return new ResponseEntity<>(depositoAguaService.findByZonaRiego(zonaRiegoRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/depositoAgua")
    public ResponseEntity<DepositoAgua> listaDepositoAgua(@RequestBody ZonaRiego zonaRiego) {
        try {
            DepositoAgua depositoAgua1 = depositoAguaService.findByZonaRiego(zonaRiego, "agua");
            return new ResponseEntity<>(depositoAgua1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new DepositoAgua(), HttpStatus.OK);
        }
    }

    @PostMapping("/depositoNutrientes")
    public ResponseEntity<DepositoAgua> listaDepositoNutrientes(@RequestBody ZonaRiego zonaRiego) {
        try {
            return new ResponseEntity<>(depositoAguaService.findByZonaRiego(zonaRiego, "nutrientes"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new DepositoAgua(), HttpStatus.OK);
        }
    }

    @PostMapping("/deposito")
    public ResponseEntity<List<DepositoAgua>> busquedadireccionMac(@RequestBody DepositoAgua depositoAguaRequest) {
        try {
            if (depositoAguaRequest == null)
                return new ResponseEntity<>(depositoAguaService.findAll(), HttpStatus.OK);
            else
                return new ResponseEntity<>(depositoAguaService.findByDescripcion(depositoAguaRequest.getDescripcion()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(depositoAguaService.findAll(), HttpStatus.OK);
        }
    }

    @GetMapping("/{direccionMAC}")
    public ResponseEntity<DepositoAgua> buscarDireccionMac(@PathVariable("direccionMAC") String direccionMAC) {
        try {
            depositoAgua = depositoAguaService.findByDireccionMAC(direccionMAC);
            if (depositoAgua == null)
                return new ResponseEntity<>(new DepositoAgua(-1L, "Ocurrió un error inesperado."), HttpStatus.CONFLICT);
            return new ResponseEntity<>(depositoAgua, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new DepositoAgua(-1L, "Ocurrió un error inesperado."), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/cantidaLitrosNutrientes")
    public ResponseEntity<Double> cantidaLitrosNutrientes(@RequestBody ZonaRiego zonaRiego) {
        try {
            return new ResponseEntity<>(depositoAguaService.cantidadLiquido(zonaRiego, "nutrientes"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(-1.0, HttpStatus.OK);
        }
    }

    @PostMapping("/cantidaLitrosAgua")
    public ResponseEntity<Double> cantidaLitrosAgua(@RequestBody ZonaRiego zonaRiego) {
        try {
            return new ResponseEntity<>(depositoAguaService.cantidadLiquido(zonaRiego, "agua"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(-1.0, HttpStatus.OK);
        }
    }

    @PostMapping("/findByIdTanqueNutrientes")
    public ResponseEntity<Long> findByIdTanqueNutrientes(@RequestBody ZonaRiego zonaRiego) {
        try {
            return new ResponseEntity<>(depositoAguaService.findByIdTanque(zonaRiego, "nutrientes"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(-1L, HttpStatus.OK);
        }
    }

    @PostMapping("/findByIdTanqueAgua")
    public ResponseEntity<Long> findByIdTanqueAgua(@RequestBody ZonaRiego zonaRiego) {
        try {
            return new ResponseEntity<>(depositoAguaService.findByIdTanque(zonaRiego, "agua"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(-1L, HttpStatus.OK);
        }
    }

    @GetMapping("/habilitarMedicionTanque/{habilitarMedicion}")
    public ResponseEntity<Boolean> habilitarMedicionTanque(@PathVariable("habilitarMedicion") int deposito) {
        try {
            return new ResponseEntity<>(depositoAguaService.habilitarMedicionTanque(deposito), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/desabilitarMedicion/{desabilitarMedicion}")
    public ResponseEntity<Boolean> desabilitarMedicion(@PathVariable("desabilitarMedicion") int deposito) {
        try {
            return new ResponseEntity<>(depositoAguaService.desabilitarMedicionTanque(deposito), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/estadoMedicion/{estadoMedicion}")
    public ResponseEntity<Boolean> estadoMedicion(@PathVariable("estadoMedicion") int deposito) {
        try {
            return new ResponseEntity<>(depositoAguaService.estadoMedicion(deposito), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.CONFLICT);
        }
    }

}
