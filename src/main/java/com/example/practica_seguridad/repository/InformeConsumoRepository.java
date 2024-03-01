package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.DepositoAgua;
import com.example.practica_seguridad.model.InformeConsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface InformeConsumoRepository extends JpaRepository<InformeConsumo,Integer> {
    List<InformeConsumo> findByDepositoAguaAndFechaCosumo(DepositoAgua depositoAgua, Date fechaMedicion);
    @Query("SELECT ic FROM InformeConsumo ic WHERE ic.depositoAgua=:depositoAgua AND DATE(ic.fechaCosumo) = :fecha")
    List<InformeConsumo> findByDepositoAguaAndFecha(@Param("depositoAgua") DepositoAgua depositoAgua, @Param("fecha") Date fecha);
}
