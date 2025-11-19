// src/main/java/py/edu/uc/lp32025/dto/EmpleadoDTO.java
package py.edu.uc.lp32025.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera constructor vacío
@AllArgsConstructor // Genera constructor con todos los argumentos
@Builder // Habilita el patrón Builder de Lombok
public class EmpleadoDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String numeroDocumento;
    private String tipoEmpleado; // "EmpleadoTiempoCompleto", "EmpleadoPorHora", "Contratista"
    private BigDecimal salario; // Salario calculado
    private String informacionEspecifica; // Información específica según el tipo
    private String numeroEmpleado; // Campo añadido para identificación
}