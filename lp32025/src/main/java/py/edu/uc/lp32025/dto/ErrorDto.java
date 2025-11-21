// src/main/java/py/edu/uc/lp32025/dto/ErrorDto.java
package py.edu.uc.lp32025.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Genera Getters, Setters, toString, equals, hashCode automáticamente
@Builder
@NoArgsConstructor // Genera el constructor vacío
@AllArgsConstructor // Genera el constructor con todos los argumentos
public class ErrorDto {
    private int codigoEstado;
    private String mensaje;
    private String mensajeUsuario;
    private String timestamp;

    // Constructor personalizado opcional (Corregido para convertir a String)
    public ErrorDto(int codigoEstado, String mensaje, String mensajeUsuario) {
        this.codigoEstado = codigoEstado;
        this.mensaje = mensaje;
        this.mensajeUsuario = mensajeUsuario;
        // CORRECCIÓN: Convertimos la fecha a String aquí mismo para evitar el error
        this.timestamp = LocalDateTime.now().toString();
    }

    // NOTA: Se eliminaron los Getters, Setters y toString manuales
    // porque @Data ya los crea correctamente con el tipo String.
}