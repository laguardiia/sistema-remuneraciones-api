// src/main/java/py/edu/uc/lp32025/service/ContratistaService.java
package py.edu.uc.lp32025.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.Contratista;
import py.edu.uc.lp32025.repository.ContratistaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ContratistaService {

    @Autowired
    private ContratistaRepository contratistaRepository;

    public Contratista guardarContratista(Contratista contratista) {
        // Validaciones manuales
        if (contratista.getMontoPorProyecto() == null || contratista.getMontoPorProyecto().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto por proyecto no puede ser nulo o negativo");
        }

        if (contratista.getProyectosCompletados() == null || contratista.getProyectosCompletados() < 0) {
            throw new IllegalArgumentException("Los proyectos completados no pueden ser nulos o negativos");
        }

        if (contratista.getFechaFinContrato() == null) {
            throw new IllegalArgumentException("La fecha de fin de contrato es obligatoria");
        }

        if (contratista.getFechaFinContrato().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin de contrato no puede ser en el pasado");
        }

        return contratistaRepository.save(contratista);
    }

    public List<Contratista> findAll() {
        return contratistaRepository.findAll();
    }

    public Contratista findById(Long id) {
        return contratistaRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        contratistaRepository.deleteById(id);
    }
}