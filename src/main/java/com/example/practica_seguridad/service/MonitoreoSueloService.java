package com.example.practica_seguridad.service;

import com.example.practica_seguridad.model.ChartDataSuelo;
import com.example.practica_seguridad.model.ChartDataTemperatura;
import com.example.practica_seguridad.model.MonitoreoSuelo;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.interfaces.IMonitoreoSueloService;
import com.example.practica_seguridad.repository.MonitoreoSueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MonitoreoSueloService implements IMonitoreoSueloService {
    @Autowired
    private MonitoreoSueloRepository monitoreoSueloRepository;

    @Override
    @Transactional
    public MonitoreoSuelo create(MonitoreoSuelo monitoreoSuelo) {
        try {
            monitoreoSuelo.setFechaMedicion(new Date());
            return monitoreoSueloRepository.save(monitoreoSuelo);
        } catch (Exception e) {
            return new MonitoreoSuelo(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public MonitoreoSuelo update(MonitoreoSuelo monitoreoSuelo) {
        try {
            return monitoreoSueloRepository.save(monitoreoSuelo);
        } catch (Exception e) {
            return new MonitoreoSuelo(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public MonitoreoSuelo findById(Integer idMonitoreoSuelo) {
        try {
            Optional<MonitoreoSuelo> monitoreoSuelo = monitoreoSueloRepository.findById(idMonitoreoSuelo);
            return monitoreoSuelo.orElse(null);
        } catch (Exception e) {
            return new MonitoreoSuelo(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<MonitoreoSuelo> findAll() {
        try {
            return monitoreoSueloRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public List<MonitoreoSuelo> findAllSuelo(ZonaRiego zonaRiego) {
        try {
            return monitoreoSueloRepository.findByZonaRiego(zonaRiego)
                    .orElseThrow(() -> new IllegalArgumentException("No ambient temperature log available."));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idMonitoreoSuelo) {
        try {
            monitoreoSueloRepository.deleteById(idMonitoreoSuelo);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public List<MonitoreoSuelo> findByFecha(ZonaRiego zonaRiego, Date fecha) {
        try {
            return monitoreoSueloRepository.findByZonaRiegoAndFechaMedicion(zonaRiego, fecha);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    @Transactional
    public List<Date> findByFechas(ZonaRiego zonaRiego, Date fecha) {
        try {
            SimpleDateFormat formatoMes = new SimpleDateFormat("MMMM", new Locale("es", "ES"));
            String mes = formatoMes.format(fecha);
            List<Date> fechasMeses = monitoreoSueloRepository.findDistinctFechaMedicionByZonaRiego(mes, zonaRiego);
            if (fechasMeses.size() < 1) {
                formatoMes = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                mes = formatoMes.format(fecha);
                fechasMeses = monitoreoSueloRepository.findDistinctFechaMedicionByZonaRiego(mes, zonaRiego);
            }
            return fechasMeses;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    @Transactional
    public List<Integer> findByAnio(ZonaRiego zonaRiego) {
        try {
            return monitoreoSueloRepository.findAnioMedicionByZonaRiego(zonaRiego);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    @Transactional
    public List<String> findByMeses(ZonaRiego zonaRiego, Date fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            return monitoreoSueloRepository.findFechaMedicionByZonaRiego(sdf.format(fecha), zonaRiego);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public ChartDataSuelo obtenerDatosSueloPorMeses(ZonaRiego zonaRiego, Date fecha) {
        try {
            SimpleDateFormat formatoMes = new SimpleDateFormat("MMMM", new Locale("es", "ES"));
            String mes = formatoMes.format(fecha);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            int anio = calendar.get(Calendar.YEAR);
            List<Object> resultados = monitoreoSueloRepository.obtenerDatosTemperaturaPorMes(mes, anio, zonaRiego);
            if (resultados.size() < 1) {
                formatoMes = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                mes = formatoMes.format(fecha);
                resultados = monitoreoSueloRepository.obtenerDatosTemperaturaPorMesEnglish(mes, anio, zonaRiego);
            }
            List<String> meses = new ArrayList<>();
            List<Double> medicionesFosforo = new ArrayList<>();
            List<Double> medicionesPotasio = new ArrayList<>();
            List<Double> medicionesNitrogeno = new ArrayList<>();
            List<Double> medicionesHumedad = new ArrayList<>();
            for (Object resultado : resultados) {
                Object[] resultadoArray = (Object[]) resultado;
                String dia = ((String) resultadoArray[0]);
                Double avgFosforo = (Double) resultadoArray[2];
                Double avgPotasio = (Double) resultadoArray[3];
                Double avgNitrogeno = (Double) resultadoArray[4];
                Double avgHumedad = (Double) resultadoArray[5];
                DecimalFormat df = new DecimalFormat("#.##");
                avgFosforo = Double.valueOf(df.format(avgFosforo));
                avgPotasio = Double.valueOf(df.format(avgPotasio));
                avgNitrogeno = Double.valueOf(df.format(avgNitrogeno));
                avgHumedad = Double.valueOf(df.format(avgHumedad));
                Long idZona = (Long) resultadoArray[6];
                meses.add(dia);
                medicionesFosforo.add(avgFosforo);
                medicionesPotasio.add(avgPotasio);
                medicionesNitrogeno.add(avgNitrogeno);
                medicionesHumedad.add(avgHumedad);
            }
            return new ChartDataSuelo(meses, medicionesFosforo, medicionesPotasio, medicionesNitrogeno, medicionesHumedad);
        } catch (Exception e) {
            return new ChartDataSuelo();
        }
    }

    @Transactional
    public ChartDataSuelo obtenerDatosSueloPorFecha(ZonaRiego zonaRiego, Date fecha) {
        try {
            List<Object> resultados = monitoreoSueloRepository.obtenerDatosTemperaturaPorFechaYHoraPromedio(fecha, Math.toIntExact(zonaRiego.getIdZona()));
            List<String> horas = new ArrayList<>();
            List<Double> medicionesFosforo = new ArrayList<>();
            List<Double> medicionesPotasio = new ArrayList<>();
            List<Double> medicionesNitrogeno = new ArrayList<>();
            List<Double> medicionesHumedad = new ArrayList<>();
            for (Object resultado : resultados) {
                Object[] resultadoArray = (Object[]) resultado;
                Long hour = ((Integer) resultadoArray[0]).longValue();
                Double avgFosforo = (Double) resultadoArray[2];
                Double avgPotasio = (Double) resultadoArray[3];
                Double avgNitrogeno = (Double) resultadoArray[4];
                Double avgHumedad = (Double) resultadoArray[5];
                DecimalFormat df = new DecimalFormat("#.##");
                avgFosforo = Double.valueOf(df.format(avgFosforo));
                avgPotasio = Double.valueOf(df.format(avgPotasio));
                avgNitrogeno = Double.valueOf(df.format(avgNitrogeno));
                avgHumedad = Double.valueOf(df.format(avgHumedad));
                String horaFormateada = (hour > 12) ? (hour - 12) + " PM" : hour + " AM";
                horas.add(horaFormateada);
                medicionesFosforo.add(avgFosforo);
                medicionesPotasio.add(avgPotasio);
                medicionesNitrogeno.add(avgNitrogeno);
                medicionesHumedad.add(avgHumedad);
            }
            return new ChartDataSuelo(horas, medicionesFosforo, medicionesPotasio, medicionesNitrogeno, medicionesHumedad);
        } catch (Exception e) {
            return new ChartDataSuelo();
        }
    }

    @Transactional
    public ChartDataSuelo obtenerDatosSueloPorAnio(ZonaRiego zonaRiego, Date fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            List<Object> resultados = monitoreoSueloRepository.obtenerDatosTemperaturaPorAnio(sdf.format(fecha), zonaRiego);
            List<String> meses = new ArrayList<>();
            List<Double> medicionesFosforo = new ArrayList<>();
            List<Double> medicionesPotasio = new ArrayList<>();
            List<Double> medicionesNitrogeno = new ArrayList<>();
            List<Double> medicionesHumedad = new ArrayList<>();
            for (Object resultado : resultados) {
                Object[] resultadoArray = (Object[]) resultado;
                String mes = ((String) resultadoArray[0]);
                Double avgFosforo = (Double) resultadoArray[2];
                Double avgPotasio = (Double) resultadoArray[3];
                Double avgNitrogeno = (Double) resultadoArray[4];
                Double avgHumedad = (Double) resultadoArray[5];
                DecimalFormat df = new DecimalFormat("#.##");
                avgFosforo = Double.valueOf(df.format(avgFosforo));
                avgPotasio = Double.valueOf(df.format(avgPotasio));
                avgNitrogeno = Double.valueOf(df.format(avgNitrogeno));
                avgHumedad = Double.valueOf(df.format(avgHumedad));
                meses.add(mes);
                medicionesFosforo.add(avgFosforo);
                medicionesPotasio.add(avgPotasio);
                medicionesNitrogeno.add(avgNitrogeno);
                medicionesHumedad.add(avgHumedad);
            }
            return new ChartDataSuelo(meses, medicionesFosforo, medicionesPotasio, medicionesNitrogeno, medicionesHumedad);
        } catch (Exception e) {
            return new ChartDataSuelo();
        }
    }


}
