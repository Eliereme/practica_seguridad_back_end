package com.example.practica_seguridad.service;

import com.example.practica_seguridad.model.DepositoAgua;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.repository.DepositoAguaRepository;
import com.example.practica_seguridad.interfaces.IDepositoAgua;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepositoAguaService implements IDepositoAgua {
    @Autowired
    private DepositoAguaRepository depositoAguaService;

    @Override
    @Transactional
    public DepositoAgua create(DepositoAgua depositoAgua) {
        try {
            List<DepositoAgua> depositoAguas = depositoAguaService.findByDireccionMAC(depositoAgua.getDireccionMAC());
            if (depositoAguas.size() > 0) {
                for (DepositoAgua agua : depositoAguas) {
                    agua.setEstado(false);
                }
                depositoAguaService.saveAll(depositoAguas);
            }
            depositoAgua.setEstado(true);
            return depositoAguaService.save(depositoAgua);
        } catch (Exception e) {
            return new DepositoAgua(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public DepositoAgua update(DepositoAgua depositoAgua) {
        try {
            depositoAgua.setEstadoTanque(depositoAgua.getNivelAgua() > 0);
            return depositoAguaService.save(depositoAgua);
        } catch (Exception e) {
            return new DepositoAgua(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public DepositoAgua findById(Integer idDepositoAgua) {
        try {
            Optional<DepositoAgua> depositoAgua = depositoAguaService.findById(idDepositoAgua);
            return depositoAgua.orElse(null);
        } catch (Exception e) {
            return new DepositoAgua(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<DepositoAgua> findAll() {
        try {
            return depositoAguaService.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idSensor) {
        try {
            depositoAguaService.deleteById(idSensor);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public List<DepositoAgua> findByDescripcion(String descripcion) {
        try {
            return depositoAguaService.findByDescripcionContainingIgnoreCase(descripcion);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<DepositoAgua> findByZonaRiego(ZonaRiego zonaRiego) {
        try {
            return depositoAguaService.findByZonaRiego(zonaRiego).stream()
                    .filter(DepositoAgua::getEstado)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    @Transactional
    public DepositoAgua findByZonaRiego(ZonaRiego zonaRiego, String liquido) {
        try {
            DepositoAgua tanqueNutriente = new DepositoAgua();
            DepositoAgua tanqueNutrienteMayor = new DepositoAgua();
            List<DepositoAgua> depositoAguas = depositoAguaService.findByZonaRiegoAndLiquido(zonaRiego, liquido);
            for (DepositoAgua depositoNutriente : depositoAguas) {
                if (depositoAguas.size() == 1) {
                    tanqueNutriente = depositoNutriente;
                    break;
                } else {
                    if (!depositoNutriente.getEstadoTanque()) {
                        tanqueNutriente = depositoNutriente;
                        break;
                    } else {
                        if (tanqueNutrienteMayor.getNivelAgua() > depositoNutriente.getNivelAgua()) {
                            tanqueNutriente = tanqueNutrienteMayor;
                        } else {
                            tanqueNutrienteMayor = depositoNutriente;
                            tanqueNutriente = depositoNutriente;
                        }
                    }
                }
            }
            return tanqueNutriente;
        } catch (Exception e) {
            return new DepositoAgua();
        }
    }

    @Transactional
    public double cantidadLiquido(ZonaRiego zonaRiego, String liquido) {
        try {
            DepositoAgua tanque = findByZonaRiego(zonaRiego, liquido);
            return tanque.getCantidadLiquido() > 0 ? tanque.getCantidadLiquido() : 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Transactional
    public Long findByIdTanque(ZonaRiego zonaRiego, String liquido) {
        try {
            DepositoAgua tanque = findByZonaRiego(zonaRiego, liquido);
            return tanque.getIdDeposito();
        } catch (Exception e) {
            return -1L;
        }

    }

    @Transactional
    public Boolean habilitarMedicionTanque(int deposito) {
        try {
            DepositoAgua tanque = findById(deposito);
            tanque.setMedicionLiquido(true);
            update(tanque);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean desabilitarMedicionTanque(int deposito) {
        try {
            DepositoAgua tanque = findById(deposito);
            tanque.setMedicionLiquido(false);
            update(tanque);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean estadoMedicion(int deposito) {
        try {
            DepositoAgua tanque = findById(deposito);
            return tanque.isMedicionLiquido();
        } catch (Exception e) {
            return false;
        }
    }


    @Transactional
    public DepositoAgua findByDireccionMAC(String direccionMac) {
        try {
            DepositoAgua agua = new DepositoAgua();
            List<DepositoAgua> depositoAguas = depositoAguaService.findByDireccionMAC(direccionMac);
            for (DepositoAgua depositoAgua : depositoAguas) {
                if (depositoAgua.getEstado()) {
                    agua = depositoAgua;
                }
            }
            return agua;
        } catch (Exception e) {
            return new DepositoAgua(-1L, e.getMessage());
        }
    }
}
