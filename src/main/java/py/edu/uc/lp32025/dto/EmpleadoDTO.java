// src/main/java/py/edu/uc/lp32025/dto/EmpleadoDTO.java
package py.edu.uc.lp32025.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class EmpleadoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaNacimiento;
    private String numeroDocumento;
    private String numeroEmpleado;
    private String tipoEmpleado;
    private BigDecimal salario;
    private String informacionEspecifica;

    // ✅ Nuevo campo para mostrar saldo
    private Integer diasVacacionesDisponibles;
}