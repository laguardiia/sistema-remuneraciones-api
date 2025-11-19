// src/main/java/py/edu/uc/lp32025/repository/EmpleadoPorHorasRepository.java
package py.edu.uc.lp32025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;

import java.util.List;

public interface EmpleadoPorHoraRepository extends JpaRepository<EmpleadoPorHora, Long> {

    // ✅ Método para buscar empleados con más de X horas trabajadas
    List<EmpleadoPorHora> findByHorasTrabajadasGreaterThan(Integer horasMinimas);
}