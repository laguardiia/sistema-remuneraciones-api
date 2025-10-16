// src/main/java/py/edu/uc/lp32025/controller/EmpleadoTiempoCompletoController.java
package py.edu.uc.lp32025.controller;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.service.EmpleadoTiempoCompletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoTiempoCompletoController {

    @Autowired
    private EmpleadoTiempoCompletoService empleadoService;

    @GetMapping
    public List<EmpleadoTiempoCompleto> listarTodos() {
        return empleadoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoTiempoCompleto> obtenerPorId(@PathVariable Long id) {
        EmpleadoTiempoCompleto empleado = empleadoService.findById(id);
        if (empleado != null) {
            return ResponseEntity.ok(empleado);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EmpleadoTiempoCompleto> crearEmpleado(@RequestBody EmpleadoTiempoCompleto empleado) {
        try {
            EmpleadoTiempoCompleto empleadoGuardado = empleadoService.guardarEmpleado(empleado);
            URI location = URI.create("/api/empleados/" + empleadoGuardado.getId());
            return ResponseEntity.created(location).body(empleadoGuardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/departamento/{departamento}")
    public List<EmpleadoTiempoCompleto> findByDepartamento(@PathVariable String departamento) {
        return empleadoService.findByDepartamento(departamento);
    }

    @GetMapping("/salario-mayor/{salario}")
    public List<EmpleadoTiempoCompleto> findBySalarioMayor(@PathVariable BigDecimal salario) {
        return empleadoService.findBySalarioMayor(salario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}