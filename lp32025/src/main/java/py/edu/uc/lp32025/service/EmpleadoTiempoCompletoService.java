// src/main/java/py/edu/uc/lp32025/service/EmpleadoTiempoCompletoService.java
package py.edu.uc.lp32025.service;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.repository.EmpleadoTiempoCompletoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EmpleadoTiempoCompletoService {

    @Autowired
    private EmpleadoTiempoCompletoRepository empleadoRepository;

    public EmpleadoTiempoCompleto guardarEmpleado(EmpleadoTiempoCompleto empleado) {
        // Validaciones manuales
        if (empleado.getSalarioMensual() == null || empleado.getSalarioMensual().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El salario no puede ser nulo o negativo");
        }

        if (empleado.getDepartamento() == null || empleado.getDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("El departamento es obligatorio");
        }

        return empleadoRepository.save(empleado);
    }

    public List<EmpleadoTiempoCompleto> findAll() {
        return empleadoRepository.findAll();
    }

    public EmpleadoTiempoCompleto findById(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    public List<EmpleadoTiempoCompleto> findByDepartamento(String departamento) {
        return empleadoRepository.findByDepartamento(departamento);
    }

    public List<EmpleadoTiempoCompleto> findBySalarioMayor(BigDecimal salario) {
        return empleadoRepository.findBySalarioMensualGreaterThan(salario);
    }

    public void deleteById(Long id) {
        empleadoRepository.deleteById(id);
    }
}