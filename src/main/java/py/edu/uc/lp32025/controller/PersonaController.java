// src/main/java/py/edu/uc/lp32025/controller/PersonaController.java
package py.edu.uc.lp32025.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.service.RemuneracionesService;// ✅ Importar el servicio
import py.edu.uc.lp32025.repository.PersonaRepository;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para gestionar las operaciones de la entidad Persona.
 */
@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private RemuneracionesService remuneracionesService; // ✅ Inyectar el servicio

    /**
     * Endpoint para obtener una lista de todas las personas o filtrar por nombre.
     * Responde a peticiones GET en "/api/personas".
     */
    @GetMapping
    public List<Persona> listarTodas(
            @RequestParam(required = false) String nombre) { // ✅ Parámetro opcional para filtrar

        if (nombre != null && !nombre.trim().isEmpty()) {
            // Filtrar personas por nombre (case insensitive)
            return remuneracionesService.filtrarPersonasPorNombre(nombre);
        } else {
            // Si no se proporciona nombre, devolver todas las personas
            return personaRepository.findAll();
        }
    }

    /**
     * Endpoint para crear una nueva persona con validación de fecha de nacimiento.
     * Responde a peticiones POST en "/api/personas".
     * @param nuevaPersona Los datos de la persona a crear, enviados en el cuerpo de la petición en formato JSON.
     * @return La persona recién creada con su ID asignado y un código 201 (Created),
     * o un error 400 (Bad Request) si la fecha de nacimiento no es válida.
     */
    @PostMapping
    public ResponseEntity<?> crearPersona(@RequestBody Persona nuevaPersona) {
        // --- INICIO DE LA VALIDACIÓN DE FECHA DE NACIMIENTO ---
        LocalDate fechaNacimiento = nuevaPersona.getFechaNacimiento();
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaLimiteAntigua = fechaActual.minusYears(120);

        // Validar que la fecha de nacimiento no sea nula.
        if (fechaNacimiento == null) {
            return ResponseEntity.badRequest().body("El campo 'fechaNacimiento' es obligatorio.");
        }

        // Validar que la fecha de nacimiento no esté en el futuro.
        if (fechaNacimiento.isAfter(fechaActual)) {
            return ResponseEntity.badRequest().body("La fecha de nacimiento no puede ser una fecha futura.");
        }

        // Validar que la persona no tenga más de 120 años.
        if (fechaNacimiento.isBefore(fechaLimiteAntigua)) {
            return ResponseEntity.badRequest().body("La fecha de nacimiento no puede ser de más de 120 años en el pasado.");
        }
        // --- FIN DE LA VALIDACIÓN ---

        // Si las validaciones pasan, guardamos la persona.
        Persona personaGuardada = personaRepository.save(nuevaPersona);

        URI location = URI.create("/api/personas/" + personaGuardada.getId());

        return ResponseEntity.created(location).body(personaGuardada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPorId(@PathVariable Long id) {
        return personaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody Persona persona) {
        if (!personaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        persona.setId(id);
        Persona personaActualizada = personaRepository.save(persona);
        return ResponseEntity.ok(personaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long id) {
        if (!personaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        personaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}