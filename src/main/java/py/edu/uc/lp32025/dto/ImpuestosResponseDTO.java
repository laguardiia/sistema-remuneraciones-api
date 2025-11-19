// src/main/java/py/edu/uc/lp32025/dto/ImpuestosResponseDTO.java
package py.edu.uc.lp32025.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera constructor vacío
@AllArgsConstructor // Genera constructor con todos los argumentos
@Builder // Habilita el patrón Builder de Lombok
public class ImpuestosResponseDTO {
    private Long id;
    private String nombreCompleto;
    private String departamento;
    private BigDecimal salarioBruto;
    private BigDecimal salarioConDescuento;
    private BigDecimal deducciones;
    private BigDecimal impuestoBase;
    private BigDecimal impuestosTotales;
}