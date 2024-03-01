package com.example.practica_seguridad.service;

import com.example.practica_seguridad.model.ConsumoTanque;
import com.example.practica_seguridad.model.DepositoAgua;
import com.example.practica_seguridad.model.InformeConsumo;
import com.example.practica_seguridad.interfaces.IInformeConsumo;
import com.example.practica_seguridad.model.Notificacion;
import com.example.practica_seguridad.repository.InformeConsumoRepository;
import com.example.practica_seguridad.repository.MonitoreoSueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InformeConsumoService implements IInformeConsumo {
    @Autowired
    private InformeConsumoRepository informeConsumoRepository;
    @Autowired
    private NotificacionesService notificacionesService;
    @Autowired
    private DepositoAguaService depositoAguaService;

    @Override
    @Transactional
    public InformeConsumo create(InformeConsumo informeConsumo) {
        try {
            if (informeConsumo.getCantidadRestante() <= 0) {
                if (informeConsumo.getDepositoAgua().getLiquido().equals("agua")) {
                    notificacionesService.create(new Notificacion(0L, new Date(), "Los niveles del tanque de agua estan en cero.", false, "Sensor nivel agua", informeConsumo.getDepositoAgua().getZonaRiego()));
                } else {
                    notificacionesService.create(new Notificacion(0L, new Date(), "Los niveles del tanque de nutrientes estan en cero.", false, "Sensor nivel nutrientes", informeConsumo.getDepositoAgua().getZonaRiego()));
                }
            }
            DepositoAgua depositoAgua = informeConsumo.getDepositoAgua();
            depositoAgua.setNivelAgua(informeConsumo.getCantidadRestante());
            depositoAgua.setPorcentaje((informeConsumo.getCantidadRestante() / depositoAgua.getCapacidadTanque()) * 100);
            depositoAgua.setEstadoTanque(false);
            depositoAgua.setMedicionLiquido(false);
            depositoAguaService.update(depositoAgua);
            return informeConsumoRepository.save(informeConsumo);
        } catch (Exception e) {
            return new InformeConsumo(-1L, 0.0);
        }
    }

    @Transactional
    public Boolean crearInformeCosumo(ConsumoTanque consumoTanque) {
        try {
            DepositoAgua depositoAgua = depositoAguaService.findById(Math.toIntExact(consumoTanque.getIdTanque()));
            InformeConsumo informeConsumo = new InformeConsumo(0L, consumoTanque.getFechaCosumo(), consumoTanque.getCantidadConsumo()
                    , consumoTanque.getTiempoRiego(), consumoTanque.getCantidadRestante(), consumoTanque.getEstadoTanque(), depositoAgua);
            create(informeConsumo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public InformeConsumo update(InformeConsumo informeConsumo) {
        try {
            return informeConsumoRepository.save(informeConsumo);
        } catch (Exception e) {
            return new InformeConsumo(-1L, 0.0);
        }
    }

    @Override
    @Transactional
    public InformeConsumo findById(Integer idInformeConsumo) {
        try {
            Optional<InformeConsumo> usuario = informeConsumoRepository.findById(idInformeConsumo);
            return usuario.orElse(null);
        } catch (Exception e) {
            return new InformeConsumo(-1L, 0.0);
        }
    }

    @Override
    @Transactional
    public List<InformeConsumo> findAll() {
        try {
            return informeConsumoRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idInformeConsumo) {
        try {
            informeConsumoRepository.deleteById(idInformeConsumo);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public List<InformeConsumo> findByFecha(DepositoAgua depositoAgua, Date fecha) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaFormateada = formatter.parse(fecha);
            System.out.println("Fecha formateada: " + fechaFormateada);
            System.out.println("Fecha formateada: " + fecha);
            return informeConsumoRepository.findByDepositoAguaAndFecha(depositoAgua, fecha);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
