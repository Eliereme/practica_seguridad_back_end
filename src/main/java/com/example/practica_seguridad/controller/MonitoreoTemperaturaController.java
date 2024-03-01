package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.ChartDataTemperatura;
import com.example.practica_seguridad.model.FiltroMonitoreoSuelo;
import com.example.practica_seguridad.model.MonitoreoTemperatura;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.service.MonitoreoTemperaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/MonitoreoTemperatura")
public class MonitoreoTemperaturaController {
    @Autowired
    private MonitoreoTemperaturaService monitoreoTemperaturaService;

    @GetMapping("/monitoreotemperaturas")
    public ResponseEntity<List<MonitoreoTemperatura>> listaMonitoreo() {
        try {
            return new ResponseEntity<>(monitoreoTemperaturaService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<MonitoreoTemperatura> createrMonitoreoTemperatura(@RequestBody MonitoreoTemperatura monitoreoTemperatura) {
        try {
            if (monitoreoTemperatura != null)
                return new ResponseEntity<>(monitoreoTemperaturaService.create(monitoreoTemperatura), HttpStatus.OK);
            else
                return new ResponseEntity<>(new MonitoreoTemperatura(-1L), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MonitoreoTemperatura(-1L), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<MonitoreoTemperatura> updateMonitoreoTemperatura(@RequestBody MonitoreoTemperatura monitoreoTemperatura) {
        try {
            if (monitoreoTemperatura != null)
                return new ResponseEntity<>(monitoreoTemperaturaService.update(monitoreoTemperatura), HttpStatus.OK);
            else
                return new ResponseEntity<>(new MonitoreoTemperatura(-1L), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MonitoreoTemperatura(-1L), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MonitoreoTemperatura> delete(@PathVariable("id") Integer idMonitoreoTemperatura) throws Exception {
        try {
            MonitoreoTemperatura monitoreoSuelo = monitoreoTemperaturaService.findById(idMonitoreoTemperatura);
            if (monitoreoSuelo == null)
                return new ResponseEntity<>(new MonitoreoTemperatura(-1L), HttpStatus.CONFLICT);
            monitoreoTemperaturaService.delete(idMonitoreoTemperatura);
            return new ResponseEntity<>(monitoreoSuelo, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MonitoreoTemperatura(-1L), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/temperaturas")
    public ResponseEntity<List<MonitoreoTemperatura>> listaMonitoreoTemperatura(@RequestBody ZonaRiego zonaRiego) {
        try {
            return new ResponseEntity<>(monitoreoTemperaturaService.findAllTemperatura(zonaRiego), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/busqueda")
    public ResponseEntity<List<MonitoreoTemperatura>> busquedaMonitoreoTemperatura(@RequestBody FiltroMonitoreoSuelo filtroMonitoreoSuelo) {
        try {
            if (filtroMonitoreoSuelo == null)
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            else
                return new ResponseEntity<>(monitoreoTemperaturaService.findByFecha(filtroMonitoreoSuelo.getZonaRiego(), filtroMonitoreoSuelo.getFechaMedicion()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/fechasmediciones")
    public ResponseEntity<List<Date>> fechasTemperatura(@RequestBody FiltroMonitoreoSuelo filtroMonitoreoSuelo) {
        try {
            if (filtroMonitoreoSuelo == null)
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            else
                return new ResponseEntity<>(monitoreoTemperaturaService.findByFechas(filtroMonitoreoSuelo.getZonaRiego(), filtroMonitoreoSuelo.getFechaMedicion()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
    @PostMapping("/mesesmediciones")
    public ResponseEntity<List<String>> mesesTemperatura(@RequestBody FiltroMonitoreoSuelo filtroMonitoreoSuelo) {
        try {
            if (filtroMonitoreoSuelo == null)
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            else
                return new ResponseEntity<>(monitoreoTemperaturaService.findByMeses(filtroMonitoreoSuelo.getZonaRiego(), filtroMonitoreoSuelo.getFechaMedicion()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
    @PostMapping("/aniosmediciones")
    public ResponseEntity<List<Integer>> aniosTemperatura(@RequestBody ZonaRiego zonaRiego) {
        try {
            if (zonaRiego == null)
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            else
                return new ResponseEntity<>(monitoreoTemperaturaService.findByAnio(zonaRiego), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @PostMapping("/busquedatemperaturahora")
    public ResponseEntity<ChartDataTemperatura> busquedaTemperaturaHora(@RequestBody FiltroMonitoreoSuelo filtroMonitoreoSuelo) {
        try {
            if (filtroMonitoreoSuelo == null)
                return new ResponseEntity<>(new ChartDataTemperatura(), HttpStatus.OK);
            else
                return new ResponseEntity<>(monitoreoTemperaturaService.obtenerDatosTemperaturaPorFecha(filtroMonitoreoSuelo.getZonaRiego(), filtroMonitoreoSuelo.getFechaMedicion()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ChartDataTemperatura(), HttpStatus.OK);
        }
    }
    @PostMapping("/busquedatemperaturames")
    public ResponseEntity<ChartDataTemperatura> busquedaTemperaturaMes(@RequestBody FiltroMonitoreoSuelo filtroMonitoreoSuelo) {
        try {
            if (filtroMonitoreoSuelo == null)
                return new ResponseEntity<>(new ChartDataTemperatura(), HttpStatus.OK);
            else
                return new ResponseEntity<>(monitoreoTemperaturaService.obtenerDatosTemperaturaPorMeses(filtroMonitoreoSuelo.getZonaRiego(), filtroMonitoreoSuelo.getFechaMedicion()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ChartDataTemperatura(), HttpStatus.OK);
        }
    }
    @PostMapping("/busquedatemperaturaanio")
    public ResponseEntity<ChartDataTemperatura> obtenerDatosTemperaturaPorAnio(@RequestBody FiltroMonitoreoSuelo filtroMonitoreoSuelo) {
        try {
            if (filtroMonitoreoSuelo == null)
                return new ResponseEntity<>(new ChartDataTemperatura(), HttpStatus.OK);
            else
                return new ResponseEntity<>(monitoreoTemperaturaService.obtenerDatosTemperaturaPorAnio(filtroMonitoreoSuelo.getZonaRiego(), filtroMonitoreoSuelo.getFechaMedicion()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ChartDataTemperatura(), HttpStatus.OK);
        }
    }
}
