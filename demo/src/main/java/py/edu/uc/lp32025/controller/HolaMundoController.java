// src/main/java/py/edu/uc/lp32025/controller/HolaMundoController.java
package py.edu.uc.lp32025.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import py.edu.uc.lp32025.dto.HolaMundoResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class HolaMundoController {

    // Endpoint: /HolaMundo con parámetro "nombre" que devuelve un DTO con herencia
    @GetMapping("/HolaMundo")
    public HolaMundoResponseDto holaMundo(@RequestParam(name = "nombre", required = false, defaultValue = "Mundo") String nombre) {
        try {
            // Obtener fecha y hora actual
            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Crear mensaje personalizado
            String mensaje = "Hola, " + nombre + "!";

            // Devolver el DTO con la información estructurada
            return new HolaMundoResponseDto(mensaje, nombre, fechaHora);
        } catch (Exception e) {
            // En caso de error, devolver DTO con información de error
            HolaMundoResponseDto errorResponse = new HolaMundoResponseDto();
            errorResponse.setCodigoEstado(500);
            errorResponse.setMensajeErrorTecnico("Error interno: " + e.getMessage());
            errorResponse.setMensajeErrorUsuario("Ocurrió un error al procesar la solicitud");
            return errorResponse;
        }
    }

    // Redirección desde la raíz (/)
    @GetMapping("/")
    public RedirectView redirigirARutaHolaMundo() {
        return new RedirectView("/HolaMundo");
    }
}