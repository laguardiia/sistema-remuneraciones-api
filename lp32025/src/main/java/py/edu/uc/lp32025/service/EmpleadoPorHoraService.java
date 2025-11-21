// src/main/java/py/edu/uc/lp32025/service/EmpleadoPorHoraService.java
package py.edu.uc.lp32025.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;
import py.edu.uc.lp32025.exception.EmpleadoNoEncontradoException;
import py.edu.uc.lp32025.repository.EmpleadoPorHoraRepository;

import java.util.List;

@Service
public class EmpleadoPorHoraService {

    @Autowired
    private EmpleadoPorHoraRepository empleadoRepository;

    public List<EmpleadoPorHora> findAll() {
        return empleadoRepository.findAll();
    }

    public EmpleadoPorHora findById(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException(
                        "No se encontró el empleado por hora",
                        String.valueOf(id)
                ));
    }

    public EmpleadoPorHora guardarEmpleado(EmpleadoPorHora empleado) {
        return empleadoRepository.save(empleado);
    }

    public void deleteById(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new EmpleadoNoEncontradoException("No se puede eliminar, empleado no encontrado", String.valueOf(id));
        }
        empleadoRepository.deleteById(id);
    }
}