// src/main/java/py/edu/uc/lp32025/repository/ContratistaRepository.java
package py.edu.uc.lp32025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.uc.lp32025.domain.Contratista;

import java.time.LocalDate;
import java.util.List;

public interface ContratistaRepository extends JpaRepository<Contratista, Long> {

    // ✅ Método para buscar contratos vigentes (fecha de fin después de la fecha actual)
    List<Contratista> findByFechaFinContratoAfter(LocalDate fechaActual);

    // ✅ Método alternativo para buscar contratos que vencen antes de una fecha específica
    List<Contratista> findByFechaFinContratoBefore(LocalDate fechaLimite);
}