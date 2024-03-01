package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.MonitoreoSuelo;
import com.example.practica_seguridad.model.ZonaRiego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MonitoreoSueloRepository extends JpaRepository<MonitoreoSuelo, Integer> {
    @Query(value = "SELECT CURRENT_TIMESTAMP - INTERVAL '5 hours'", nativeQuery = true)
    Date getCurrentDatabaseDateTimeMinusFiveHours();
    Optional<List<MonitoreoSuelo>> findByZonaRiego(ZonaRiego zonaRiego);

    List<MonitoreoSuelo> findByZonaRiegoAndFechaMedicion(ZonaRiego zonaRiego, Date fechaMedicion);
    MonitoreoSuelo findTopByZonaRiegoOrderByIdSueloDesc(ZonaRiego zonaRiego);

    @Query(value = "SELECT DISTINCT( Cast(m.fechaMedicion as date)) FROM MonitoreoSuelo m " +
            "WHERE m.zonaRiego = :zonaRiego" +
            " AND Upper(to_char(m.fechaMedicion, 'TMMonth'))=Upper(:mes)" +
            " ORDER BY Cast(m.fechaMedicion as date) asc")
    List<Date> findDistinctFechaMedicionByZonaRiego(@Param("mes") String mes, @Param("zonaRiego") ZonaRiego zonaRiego);

    @Query(value = "SELECT DISTINCT to_char(m.fechaMedicion, 'TMMonth')" +
            " FROM MonitoreoSuelo m WHERE m.zonaRiego= :zonaRiego" +
            " AND EXTRACT(Year FROM m.fechaMedicion)= :fecha" +
            " ORDER BY to_char(m.fechaMedicion, 'TMMonth') DESC")
    List<String> findFechaMedicionByZonaRiego(@Param("fecha") String fecha, @Param("zonaRiego") ZonaRiego zonaRiego);

    @Query(value = "select DISTINCT(EXTRACT(YEAR FROM m.fechaMedicion)) from MonitoreoSuelo m" +
            " WHERE m.zonaRiego= :zonaRiego" +
            " ORDER BY EXTRACT(YEAR FROM m.fechaMedicion)")
    List<Integer> findAnioMedicionByZonaRiego(@Param("zonaRiego") ZonaRiego zonaRiego);

    @Query(value = "SELECT CASE WHEN X.fecha = 1 THEN 2 " +
            "        WHEN X.fecha = 2 THEN 5 " +
            "        WHEN X.fecha = 3 THEN 8 " +
            " WHEN X.fecha = 4 THEN 11" +
            " WHEN X.fecha = 5 THEN 14" +
            " WHEN X.fecha = 6 THEN 17" +
            " WHEN X.fecha = 7 THEN 20" +
            " WHEN X.fecha = 8 THEN 23" +
            "    END, :fecha, AVG(X.fosforo),AVG(X.potasio), AVG(X.nitrogeno),AVG(X.humedad) FROM (" +
            " SELECT CEIL(ROW_NUMBER() OVER (ORDER BY EXTRACT(HOUR FROM m.fecha_medicion))/ 3.0) fecha," +
            " EXTRACT(HOUR FROM m.fecha_medicion) as hora,avg(m.fosforo) fosforo, avg(m.potasio) potasio,avg(m.nitrogeno) nitrogeno, avg(m.humedad) humedad" +
            "    FROM monitoreo_suelo m" +
            "    WHERE CAST(m.fecha_medicion AS DATE) = :fecha" +
            " AND id_zona= :idZona" +
            " Group by EXTRACT(HOUR FROM m.fecha_medicion))AS X" +
            " Group by X.fecha", nativeQuery = true)
    List<Object> obtenerDatosTemperaturaPorFechaYHoraPromedio(@Param("fecha") Date fecha, @Param("idZona")Integer zonaRiego);


    @Query(value = "SELECT to_char(m.fechaMedicion, 'TMMonth') ," +
            " :fecha , AVG(m.fosforo) , AVG(m.potasio), AVG(m.nitrogeno) , AVG(m.humedad) , m.zonaRiego.idZona" +
            " FROM MonitoreoSuelo m" +
            " WHERE " +
            " EXTRACT(YEAR FROM m.fechaMedicion) = :fecha" +
            " AND m.zonaRiego = :zonaRiego " +
            " GROUP BY to_char(m.fechaMedicion, 'TMMonth'), m.zonaRiego.idZona" +
            " ORDER BY EXTRACT(MONTH FROM MIN(m.fechaMedicion)) Asc")
    List<Object> obtenerDatosTemperaturaPorAnio(@Param("fecha") String fecha, @Param("zonaRiego") ZonaRiego zonaRiego);


    @Query(value = "SELECT to_char(m.fechaMedicion, 'TMDay')," +
            " :mes , AVG(m.fosforo) , AVG(m.potasio), AVG(m.nitrogeno) , AVG(m.humedad) , m.zonaRiego.idZona" +
            " FROM MonitoreoSuelo m" +
            " WHERE " +
            " UPPER(to_char(m.fechaMedicion, 'TMMonth')) = UPPER(:mes)" +
            " AND m.zonaRiego = :zonaRiego " +
            " AND EXTRACT(YEAR FROM m.fechaMedicion)=:anio " +
            " GROUP BY to_char(m.fechaMedicion, 'TMDay'), m.zonaRiego.idZona" +
            " ORDER BY CASE" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Lunes' THEN 1" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Martes' THEN 2" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Miércoles' THEN 3" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Jueves' THEN 4" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Viernes' THEN 5" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Sábado' THEN 6" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Domingo' THEN 7 END")
    List<Object> obtenerDatosTemperaturaPorMes(@Param("mes") String mes,@Param("anio") int anio, @Param("zonaRiego") ZonaRiego zonaRiego);
    @Query(value = "SELECT to_char(m.fechaMedicion, 'TMDay')," +
            " :mes , AVG(m.fosforo) , AVG(m.potasio), AVG(m.nitrogeno) , AVG(m.humedad) , m.zonaRiego.idZona" +
            " FROM MonitoreoSuelo m" +
            " WHERE " +
            " UPPER(to_char(m.fechaMedicion, 'TMMonth')) = UPPER(:mes)" +
            " AND m.zonaRiego = :zonaRiego " +
            " AND EXTRACT(YEAR FROM m.fechaMedicion)=:anio " +
            " GROUP BY to_char(m.fechaMedicion, 'TMDay'), m.zonaRiego.idZona" +
            " ORDER BY CASE" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Monday' THEN 1" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Tuesday' THEN 2" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Wednesday' THEN 3" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Thursday' THEN 4" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Friday' THEN 5" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Saturday' THEN 6" +
            " WHEN to_char(m.fechaMedicion, 'TMDay') = 'Sunday' THEN 7 END")
    List<Object> obtenerDatosTemperaturaPorMesEnglish(@Param("mes") String mes,@Param("anio") int anio, @Param("zonaRiego") ZonaRiego zonaRiego);

}
