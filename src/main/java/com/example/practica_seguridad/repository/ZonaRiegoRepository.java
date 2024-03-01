package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.SistemaRiego;
import com.example.practica_seguridad.model.ZonaRiego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ZonaRiegoRepository extends JpaRepository<ZonaRiego, Integer> {
    Optional<List<ZonaRiego>> findBySistemaRiego(SistemaRiego sistemaRiego);

    ZonaRiego findByNombreZona(String nombreZona);
    List<ZonaRiego> findByDireccionMAC(String direccionMac);
   List<ZonaRiego> findByNombreZonaContainingIgnoreCase(String nombreZona);
    ZonaRiego findByIdZona(Integer idZona);
}
