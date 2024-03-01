package com.example.practica_seguridad.service;

import com.example.practica_seguridad.model.ChartDataTemperatura;
import com.example.practica_seguridad.model.MonitoreoTemperatura;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.interfaces.IMonitoreoTemperaturaService;
import com.example.practica_seguridad.repository.MonitoreoTemperaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MonitoreoTemperaturaService implements IMonitoreoTemperaturaService {
    @Autowired
    private MonitoreoTemperaturaRepository monitoreoTemperaturaRepository;

    @Override
    @Transactional
    public MonitoreoTemperatura create(MonitoreoTemperatura monitoreoTemperatura) {
        try {
            monitoreoTemperatura.setFechaMedicion(new Date());
            return monitoreoTemperaturaRepository.save(monitoreoTemperatura);
        } catch (Exception e) {
            return new MonitoreoTemperatura(-1L);
        }
    }

    @Override
    @Transactional
    public MonitoreoTemperatura update(MonitoreoTemperatura monitoreoTemperatura) {
        try {
            return monitoreoTemperaturaRepository.save(monitoreoTemperatura);
        } catch (Exception e) {
            return new MonitoreoTemperatura(-1L);
        }
    }

    @Override
    @Transactional
    public MonitoreoTemperatura findById(Integer idMonitoreoTemperatura) {
        try {
            Optional<MonitoreoTemperatura> monitoreoSuelo = monitoreoTemperaturaRepository.findById(idMonitoreoTemperatura);
            return monitoreoSuelo.orElse(null);
        } catch (Exception e) {
            return new MonitoreoTemperatura(-1L);
        }
    }

    @Override
    @Transactional
    public List<MonitoreoTemperatura> findAll() {
        try {
            return monitoreoTemperaturaRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public List<MonitoreoTemperatura> findAllTemperatura(ZonaRiego zonaRiego) {
        try {
            return monitoreoTemperaturaRepository.findByZonaRiego(zonaRiego)
                    .orElseThrow(() -> new IllegalArgumentException("No ambient temperature log available."));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idMonitoreoTemperatura) {
        try {
            monitoreoTemperaturaRepository.deleteById(idMonitoreoTemperatura);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public List<MonitoreoTemperatura> findByFecha(ZonaRiego zonaRiego, Date fecha) {
        try {
            return monitoreoTemperaturaRepository.findByZonaRiegoAndFechaMedicion(zonaRiego, fecha);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<Date> findByFechas(ZonaRiego zonaRiego, Date fecha) {
        try {
            SimpleDateFormat formatoMes = new SimpleDateFormat("MMMM", new Locale("es", "ES"));
            String mes = formatoMes.format(fecha);
            List<Date> fechasMeses = monitoreoTemperaturaRepository.findDistinctFechaMedicionByZonaRiego(mes, zonaRiego);
            if (fechasMeses.size() < 1) {
                formatoMes = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                mes = formatoMes.format(fecha);
                fechasMeses = monitoreoTemperaturaRepository.findDistinctFechaMedicionByZonaRiego(mes, zonaRiego);
            }
            return fechasMeses;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<Integer> findByAnio(ZonaRiego zonaRiego) {
        try {
            return monitoreoTemperaturaRepository.findAnioMedicionByZonaRiego(zonaRiego);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<String> findByMeses(ZonaRiego zonaRiego, Date fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            return monitoreoTemperaturaRepository.findFechaMedicionByZonaRiego(sdf.format(fecha), zonaRiego);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public ChartDataTemperatura obtenerDatosTemperaturaPorMeses(ZonaRiego zonaRiego, Date fecha) {
        try {
            SimpleDateFormat formatoMes = new SimpleDateFormat("MMMM", new Locale("es", "ES"));
            String mes = formatoMes.format(fecha);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            int anio = calendar.get(Calendar.YEAR);
            List<Object> resultados = monitoreoTemperaturaRepository.obtenerDatosTemperaturaPorMes(mes,anio, zonaRiego);
            if (resultados.size() < 1) {
                formatoMes = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                mes = formatoMes.format(fecha);
                resultados = monitoreoTemperaturaRepository.obtenerDatosTemperaturaPorMesEnglish(mes,anio, zonaRiego);
            }
            List<String> meses = new ArrayList<>();
            List<Double> medicionesHumedad = new ArrayList<>();
            List<Double> medicionesTemperatura = new ArrayList<>();
            for (Object resultado : resultados) {
                Object[] resultadoArray = (Object[]) resultado;
                String dia = ((String) resultadoArray[0]);
                Double avgTemperatura = (Double) resultadoArray[2];
                Double avgHumedad = (Double) resultadoArray[3];
                DecimalFormat df = new DecimalFormat("#.##");
                avgTemperatura = Double.valueOf(df.format(avgTemperatura));
                avgHumedad = Double.valueOf(df.format(avgHumedad));
                Long idZona = (Long) resultadoArray[4];
                meses.add(dia);
                medicionesTemperatura.add(avgTemperatura);
                medicionesHumedad.add(avgHumedad);
            }
            return new ChartDataTemperatura(meses, medicionesHumedad, medicionesTemperatura);
        } catch (Exception e) {
            return new ChartDataTemperatura();
        }
    }

    @Transactional
    public ChartDataTemperatura obtenerDatosTemperaturaPorFecha(ZonaRiego zonaRiego, Date fecha) {
        try {
            List<Object> resultados = monitoreoTemperaturaRepository.obtenerDatosTemperaturaPorFechaYHoraPromedio(fecha, Math.toIntExact(zonaRiego.getIdZona()));
            List<String> horas = new ArrayList<>();
            List<Double> medicionesHumedad = new ArrayList<>();
            List<Double> medicionesTemperatura = new ArrayList<>();
            for (Object resultado : resultados) {
                Object[] resultadoArray = (Object[]) resultado;
                Long hour = ((Integer) resultadoArray[0]).longValue();
                Double avgTemperatura = (Double) resultadoArray[2];
                Double avgHumedad = (Double) resultadoArray[3];
                DecimalFormat df = new DecimalFormat("#.##");
                avgTemperatura = Double.valueOf(df.format(avgTemperatura));
                avgHumedad = Double.valueOf(df.format(avgHumedad));
                String horaFormateada = (hour > 12) ? (hour - 12) + " PM" : hour + " AM";
                horas.add(horaFormateada);
                medicionesTemperatura.add(avgTemperatura);
                medicionesHumedad.add(avgHumedad);
            }
            return new ChartDataTemperatura(horas, medicionesHumedad, medicionesTemperatura);
        } catch (Exception e) {
            return new ChartDataTemperatura();
        }
    }

    @Transactional
    public ChartDataTemperatura obtenerDatosTemperaturaPorAnio(ZonaRiego zonaRiego, Date fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            List<Object> resultados = monitoreoTemperaturaRepository.obtenerDatosTemperaturaPorAnio(sdf.format(fecha), zonaRiego);
            List<String> meses = new ArrayList<>();
            List<Double> medicionesHumedad = new ArrayList<>();
            List<Double> medicionesTemperatura = new ArrayList<>();
            for (Object resultado : resultados) {
                Object[] resultadoArray = (Object[]) resultado;
                String mes = ((String) resultadoArray[0]);
                Double avgTemperatura = (Double) resultadoArray[2];
                Double avgHumedad = (Double) resultadoArray[3];
                DecimalFormat df = new DecimalFormat("#.##");
                avgTemperatura = Double.valueOf(df.format(avgTemperatura));
                avgHumedad = Double.valueOf(df.format(avgHumedad));
                meses.add(mes);
                medicionesTemperatura.add(avgTemperatura);
                medicionesHumedad.add(avgHumedad);
            }
            return new ChartDataTemperatura(meses, medicionesHumedad, medicionesTemperatura);
        } catch (Exception e) {
            return new ChartDataTemperatura();
        }
    }

}
