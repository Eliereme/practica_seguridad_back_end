package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.DepositoAgua;
import com.example.practica_seguridad.model.ZonaRiego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositoAguaRepository extends JpaRepository<DepositoAgua, Integer> {
    DepositoAgua findByLiquido(String liquido);

    List<DepositoAgua> findByDescripcionContainingIgnoreCase(String depositoAgua);

    List<DepositoAgua> findByZonaRiegoAndLiquido(ZonaRiego zonaRiego, String liquido);

    List<DepositoAgua> findByDireccionMAC(String direccionMac);

    List<DepositoAgua> findByZonaRiego(ZonaRiego zonaRiego);
}

