// src/main/java/py/edu/uc/lp32025/controller/IndexController.java
package py.edu.uc.lp32025.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import py.edu.uc.lp32025.dto.HolaMundoResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class IndexController {

    // Endpoint: /HolaMundo con parámetros que devuelve un DTO
    @GetMapping("/HolaMundo")
    public HolaMundoResponseDto holaMundo(
            @RequestParam(name = "nombre", required = false, defaultValue = "Mundo") String nombre,
            @RequestParam(name = "apellido", required = false, defaultValue = "") String apellido,
            @RequestParam(name = "edad", required = false, defaultValue = "0") int edad) {

        // Validaciones que lanzan excepciones
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        // Construir el mensaje de saludo
        StringBuilder saludo = new StringBuilder("Hola, ");
        saludo.append(nombre);

        if (!apellido.isEmpty()) {
            saludo.append(" ").append(apellido);
        }

        if (edad > 0) {
            saludo.append(" (").append(edad).append(" años)");
        }

        // Obtener fecha y hora actual
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Devolver el DTO con la información estructurada
        return new HolaMundoResponseDto(saludo.toString(), nombre, apellido, edad > 0 ? edad : null, fechaHora);
    }

    // Redirección desde la raíz (/)
    @GetMapping("/")
    public RedirectView redirigirARutaHolaMundo() {
        return new RedirectView("/HolaMundo");
    }
}