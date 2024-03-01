package com.example.practica_seguridad.service;

import com.example.practica_seguridad.interfaces.ITipoCultivo;
import com.example.practica_seguridad.model.TipoCultivo;
import com.example.practica_seguridad.repository.TipoCultivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoCultivoService implements ITipoCultivo {
    @Autowired
    private TipoCultivoRepository tipoCultivoRepository;

    @Override
    @Transactional
    public TipoCultivo create(TipoCultivo tipoCultivo) {
        try {
            if (tipoCultivoRepository.findByTipoCultivo(tipoCultivo.getTipoCultivo()) != null) {
                return new TipoCultivo(-1, "", "");
            }
            return tipoCultivoRepository.save(tipoCultivo);
        } catch (Exception e) {
            return new TipoCultivo(-1, "", e.getMessage());
        }
    }

    @Override
    @Transactional
    public TipoCultivo update(TipoCultivo tipoCultivo) {
        try {
            TipoCultivo existingCultivo = tipoCultivoRepository.findByTipoCultivo(tipoCultivo.getTipoCultivo());
            if (existingCultivo != null && !existingCultivo.getIdTipoCultivo().equals(tipoCultivo.getIdTipoCultivo())) {
                return new TipoCultivo(-1, "", "");
            }
            return tipoCultivoRepository.save(tipoCultivo);
        } catch (Exception e) {
            return new TipoCultivo(-1, "", e.getMessage());
        }
    }

    @Override
    @Transactional
    public TipoCultivo findById(Integer idTipoCultivo) {
        try{
            Optional<TipoCultivo> tipoCultivo = tipoCultivoRepository.findById(idTipoCultivo);
            return tipoCultivo.orElse(null);
        }catch (Exception e){
            return new TipoCultivo(-1, "", e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<TipoCultivo> findAll() {
        try {
            return tipoCultivoRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idTipoCultivo) {
        try {
            tipoCultivoRepository.deleteById(idTipoCultivo);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public List<TipoCultivo> findByNombre(String nombre) {
        try {
            return tipoCultivoRepository.findByTipoCultivoContainingIgnoreCase(nombre);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
