package com.example.practica_seguridad.service;

import com.example.practica_seguridad.interfaces.ICultivo;
import com.example.practica_seguridad.model.Cultivo;
import com.example.practica_seguridad.model.DetalleEnfermedad;
import com.example.practica_seguridad.model.Enfermedad;
import com.example.practica_seguridad.model.EnfermedadCultivo;
import com.example.practica_seguridad.repository.CultivoRepository;
import com.example.practica_seguridad.repository.DetalleEnfermedades;
import com.example.practica_seguridad.repository.EnfermedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CultivoService implements ICultivo {
    @Autowired
    private CultivoRepository cultivoRepository;
    @Autowired
    private DetalleEnfermedades detalleEnfermedadesRepository;
    @Autowired
    private EnfermedadRepository enfermedadRepository;

    @Override
    @Transactional
    public Cultivo create(EnfermedadCultivo cultivo) {
        Cultivo cultivoRegistro;
        try {
            if (cultivoRepository.findByNombreCultivo(cultivo.getCultivo().getNombreCultivo()) != null) {
                return new Cultivo(-1, "", "");
            }
            cultivoRegistro = cultivoRepository.save(cultivo.getCultivo());
            for (DetalleEnfermedad enfermedad : cultivo.getDetalleEnfermedades()) {
                enfermedad.setCultivo(cultivoRegistro);
            }
            detalleEnfermedadesRepository.saveAll(cultivo.getDetalleEnfermedades());
            return cultivoRegistro;
        } catch (Exception e) {
            return new Cultivo(-1, "", e.getMessage());
        }
    }

    @Override
    @Transactional
    public Cultivo update(EnfermedadCultivo cultivo) {
        try {
            Cultivo cultivoRegistro = cultivoRepository.findByIdCultivo(cultivo.getCultivo().getIdCultivo());
            Cultivo existingCultivo = cultivoRepository.findByNombreCultivo(cultivo.getCultivo().getNombreCultivo());
            if (existingCultivo != null && !existingCultivo.getIdCultivo().equals(cultivo.getCultivo().getIdCultivo())) {
                return new Cultivo(-1, "", "");
            }
            for (DetalleEnfermedad enfermedad : cultivo.getDetalleEnfermedades()) {
                enfermedad.setCultivo(cultivoRegistro);
            }
            deleteListEnfermedades(cultivo.getDetalleEnfermedades(),cultivoRegistro);
            return cultivoRepository.save(cultivo.getCultivo());
        } catch (Exception e) {
            return new Cultivo(-1, "", e.getMessage());
        }
    }

    private boolean deleteListEnfermedades(List<DetalleEnfermedad> detalleEnfermedades, Cultivo cultivo) {
        try {
            List<DetalleEnfermedad> detalleEnfermedadList = detalleEnfermedadesRepository.findByCultivo(cultivo);
            for (DetalleEnfermedad enfermedad : detalleEnfermedades) {
                if (!verificaDetalleEnfermedades(detalleEnfermedadList, enfermedad)) {
                    detalleEnfermedadesRepository.save(enfermedad);
                }
            }
            for (DetalleEnfermedad enfermedad : detalleEnfermedadList) {
                if (!verificaDetalleEnfermedades(detalleEnfermedades, enfermedad)) {
                    detalleEnfermedadesRepository.delete(enfermedad);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean verificaDetalleEnfermedades(List<DetalleEnfermedad> enfermedades, DetalleEnfermedad enfermedad) {
        try {
            for (DetalleEnfermedad detalleEnfermedad : enfermedades) {
                if (Objects.equals(detalleEnfermedad.getEnfermedad().getIdEnfermedad(), enfermedad.getEnfermedad().getIdEnfermedad())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Cultivo findById(Integer idCultivo) {
        try {
            Optional<Cultivo> cultivo = cultivoRepository.findById(idCultivo);
            return cultivo.orElse(null);
        } catch (Exception e) {
            return new Cultivo(-1, "", e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Cultivo> findAll() {
        try {
            return cultivoRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<Enfermedad> findListaEnfermedades(Cultivo cultivo) {
        try {
            List<DetalleEnfermedad> detalleEnfermedadesList = detalleEnfermedadesRepository.findByCultivo(cultivo);
            List<Integer> detalleEnfermedadIds = detalleEnfermedadesList.stream()
                    .map(detalleEnfermedad -> detalleEnfermedad.getEnfermedad().getIdEnfermedad())
                    .toList();
            return enfermedadRepository.findByDetalleEnfermedadesIn(detalleEnfermedadIds);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idCultivo) {
        try {
            cultivoRepository.deleteById(idCultivo);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public List<Cultivo> findByNombre(String nombre) {
        try {
            return cultivoRepository.findByNombreCultivoContainingIgnoreCase(nombre);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
