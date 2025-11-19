// src/main/java/py/edu/uc/lp32025/service/EmpleadoPorHoraService.java
package py.edu.uc.lp32025.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;
import py.edu.uc.lp32025.repository.EmpleadoPorHoraRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EmpleadoPorHoraService {

    @Autowired
    private EmpleadoPorHoraRepository empleadoRepository;

    public EmpleadoPorHora guardarEmpleado(EmpleadoPorHora empleado) {
        // Validaciones manuales
        if (empleado.getTarifaPorHora() == null || empleado.getTarifaPorHora().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La tarifa por hora no puede ser nula o negativa");
        }

        if (empleado.getHorasTrabajadas() == null || empleado.getHorasTrabajadas() < 0) {
            throw new IllegalArgumentException("Las horas trabajadas no pueden ser nulas o negativas");
        }

        return empleadoRepository.save(empleado);
    }

    public List<EmpleadoPorHora> findAll() {
        return empleadoRepository.findAll();
    }

    public EmpleadoPorHora findById(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        empleadoRepository.deleteById(id);
    }
}