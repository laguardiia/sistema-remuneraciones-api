// src/main/java/py/edu/uc/lp32025/controller/EmpleadoPorHoraController.java
package py.edu.uc.lp32025.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;
import py.edu.uc.lp32025.service.EmpleadoPorHoraService;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/empleados-por-hora")
public class EmpleadoPorHoraController {

    @Autowired
    private EmpleadoPorHoraService empleadoService;

    @GetMapping
    public List<EmpleadoPorHora> listarTodos() {
        return empleadoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoPorHora> obtenerPorId(@PathVariable Long id) {
        EmpleadoPorHora empleado = empleadoService.findById(id);
        if (empleado != null) {
            return ResponseEntity.ok(empleado);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EmpleadoPorHora> crearEmpleado(@RequestBody EmpleadoPorHora empleado) {
        try {
            EmpleadoPorHora empleadoGuardado = empleadoService.guardarEmpleado(empleado);
            URI location = URI.create("/api/empleados-por-hora/" + empleadoGuardado.getId());
            return ResponseEntity.created(location).body(empleadoGuardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}