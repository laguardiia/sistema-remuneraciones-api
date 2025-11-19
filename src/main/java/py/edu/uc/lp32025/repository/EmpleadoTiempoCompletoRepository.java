// src/main/java/py/edu/uc/lp32025/repository/EmpleadoTiempoCompletoRepository.java
package py.edu.uc.lp32025.repository;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmpleadoTiempoCompletoRepository extends JpaRepository<EmpleadoTiempoCompleto, Long> {

    List<EmpleadoTiempoCompleto> findByDepartamento(String departamento);

    List<EmpleadoTiempoCompleto> findBySalarioMensualGreaterThan(BigDecimal salario);
}