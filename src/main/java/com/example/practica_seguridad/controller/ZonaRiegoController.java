package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.*;
import com.example.practica_seguridad.security.TokenUtils;
import com.example.practica_seguridad.service.ZonaRiegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/zonaRiego")
public class ZonaRiegoController {
    @Autowired
    private ZonaRiegoService zonaRiegoService;
    private ZonaRiego zonaRiego;
    //@Autowired
    //private SimpMessageSendingOperations messagingTemplate;

    @GetMapping("/zonas")
    public ResponseEntity<List<ZonaRiego>> listaZonaRiego() {
        try {
            return new ResponseEntity<>(zonaRiegoService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/autorizacion")
    public String verificarZona(@RequestBody ZonaRiego zonaRiego) {
        if (zonaRiegoService.findByDireccionMac(zonaRiego.getDireccionMAC()) != null) {
            return TokenUtils.createToken(zonaRiego.getNombreZona(), String.valueOf(zonaRiego.getDireccionMAC()));
        } else {
            return "No se logro verificar el sensor";
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<ZonaRiego> createZonaRiego(@RequestBody DatosSensoresZonas zonaRiegoRequest) {
        try {
            if (zonaRiegoRequest == null) {
                return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            }
            zonaRiego = zonaRiegoService.create(zonaRiegoRequest);
            if (zonaRiego != null && zonaRiego.getIdZona() > 0) {
                return new ResponseEntity<>(zonaRiego, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ZonaRiego(-1L, zonaRiegoRequest.getZonas().getNombreZona()), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/registrozonacosecha")
    public ResponseEntity<ZonaRiego> createZonaRiegoCosecha(@RequestBody Cosecha cosechaRequest) {
        try {
            if (cosechaRequest == null) {
                return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            }
            zonaRiego = zonaRiegoService.createZonaRiegoCosecha(cosechaRequest);
            if (zonaRiego != null && zonaRiego.getIdZona() > 0) {
                return new ResponseEntity<>(zonaRiego, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ZonaRiego(-1L, cosechaRequest.getZonaRiego().getNombreZona()), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registrozona")
    public ResponseEntity<ZonaRiego> createZonaRiego(@RequestBody ZonaRiego zonaRiegoRequest) {
        try {
            if (zonaRiegoRequest == null) {
                return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            }
            zonaRiego = zonaRiegoService.createZonaRiego(zonaRiegoRequest);
            if (zonaRiego != null && zonaRiego.getIdZona() > 0) {
                return new ResponseEntity<>(zonaRiego, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ZonaRiego(-1L, zonaRiegoRequest.getNombreZona()), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<ZonaRiego> updateZonaRiego(@RequestBody DatosSensoresZonas zonaRiegoRequest) {
        try {
            if (zonaRiegoRequest == null) {
                return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            }
            zonaRiego = zonaRiegoService.update(zonaRiegoRequest);
            if (zonaRiego != null && zonaRiego.getIdZona() > 0) {
                return new ResponseEntity<>(zonaRiego, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ZonaRiego(-1L, zonaRiegoRequest.getZonas().getNombreZona()), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizarmonitoreo")
    public ResponseEntity<ZonaRiego> updateZonaRiego(@RequestBody ZonaRiego zonaRiegoRequest) {
        try {
            if (zonaRiegoRequest == null) {
                return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error. Vuelva a intentarlo luego."), HttpStatus.BAD_REQUEST);
            }
            zonaRiego = zonaRiegoService.updateZona(zonaRiegoRequest);
            if (zonaRiego != null && zonaRiego.getIdZona() > 0) {
                //messagingTemplate.convertAndSend("/topic/zonariego", zonaRiego);
                return new ResponseEntity<>(zonaRiego, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ZonaRiego(-1L, zonaRiegoRequest.getNombreZona()), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/monitoreonutrientesriego")
    public ResponseEntity<Integer> decicionRiegoNutrientes(@RequestBody ZonaRiego zonaRiegoRequest) {
        try {
            if (zonaRiegoRequest == null) {
                return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(zonaRiegoService.tomarDesiscionRiegoNutriente(zonaRiegoRequest), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/monitoreoaguariego")
    public ResponseEntity<Integer> decicionRiegoAgua(@RequestBody ZonaRiego zonaRiegoRequest) {
        try {
            if (zonaRiegoRequest == null) {
                return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(zonaRiegoService.tomarDesiscionRiegoAgua(zonaRiegoRequest), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registrarConsumo")
    public ResponseEntity<Integer> registrarConsumo(@RequestBody ZonaRiego zonaRiegoRequest) {
        try {
            if (zonaRiegoRequest == null) {
                return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(zonaRiegoService.registrarCantidadAguaConsumida(zonaRiegoRequest), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ZonaRiego> delete(@PathVariable("id") Integer idzonaRiego) throws Exception {
        try {
            zonaRiego = zonaRiegoService.findById(idzonaRiego);
            if (zonaRiego == null)
                return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error inesperado."), HttpStatus.CONFLICT);
            zonaRiegoService.delete(idzonaRiego);
            return new ResponseEntity<>(zonaRiego, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/listaZonaRiego")
    public ResponseEntity<List<ZonaRiego>> listaSistema(@RequestBody SistemaRiego sistemaRiego) {
        try {
            List<ZonaRiego> fila = zonaRiegoService.findBySistemaRiego(sistemaRiego);
            return new ResponseEntity<>(fila, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/busqueda")
    public ResponseEntity<List<ZonaRiego>> busquedaZona(@RequestBody ZonaRiego zonaRequest) {
        try {
            if (zonaRequest == null)
                return new ResponseEntity<>(zonaRiegoService.findAll(), HttpStatus.OK);
            else
                return new ResponseEntity<>(zonaRiegoService.findByNombre(zonaRequest.getNombreZona()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(zonaRiegoService.findAll(), HttpStatus.OK);
        }
    }

    @PostMapping("/busquedaZona")
    public ResponseEntity<ZonaRiego> busquedaZonaRiego(@RequestBody ZonaRiego zonaRequest) {
        try {
            if (zonaRequest == null)
                return new ResponseEntity<>(new ZonaRiego(), HttpStatus.OK);
            else
                return new ResponseEntity<>(zonaRiegoService.findByZonaRiego(zonaRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ZonaRiego(), HttpStatus.OK);
        }
    }

    @PostMapping("/listasensores")
    public ResponseEntity<List<Sensor>> listaEnfermedad(@RequestBody ZonaRiego zonaRequest) {
        try {
            return new ResponseEntity<>(zonaRiegoService.findListaSensores(zonaRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @GetMapping("/{direccionMAC}")
    public ResponseEntity<ZonaRiego> buscarDireccionMac(@PathVariable("direccionMAC") String direccionMAC) {
        try {
            zonaRiego = zonaRiegoService.findByDireccionMac(direccionMAC);
            if (zonaRiego == null)
                return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error inesperado."), HttpStatus.CONFLICT);
            return new ResponseEntity<>(zonaRiego, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ZonaRiego(-1L, "Ocurrió un error inesperado."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("idzona/{direccionMAC}")
    public ResponseEntity<Integer> buscarDireccionMacId(@PathVariable("direccionMAC") String direccionMAC) {
        try {
            return new ResponseEntity<>(zonaRiegoService.findByDireccionMacId(direccionMAC), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("verificarRiegoNutrientes/{direccionMAC}")
    public ResponseEntity<Boolean> verificarRiegoNutrientes(@PathVariable("direccionMAC") String direccionMAC) {
        try {
            return new ResponseEntity<>(zonaRiegoService.verificarRiegoNutrientes(direccionMAC), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("verificarRiegoAgua/{direccionMAC}")
    public ResponseEntity<Boolean> verificarRiegoAgua(@PathVariable("direccionMAC") String direccionMAC) {
        try {
            return new ResponseEntity<>(zonaRiegoService.verificarRiegoAgua(direccionMAC), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actvidadzona")
    public ResponseEntity<Boolean> zonaActividad(@RequestBody ZonaRiego zonaRequest) {
        try {
            return new ResponseEntity<>(zonaRiegoService.monitoreoConexion(zonaRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
