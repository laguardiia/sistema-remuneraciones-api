// src/main/java/py/edu/uc/lp32025/controller/RemuneracionesController.java
package py.edu.uc.lp32025.controller;

import py.edu.uc.lp32025.dto.EmpleadoDTO;
import py.edu.uc.lp32025.service.RemuneracionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/remuneraciones")
public class RemuneracionesController {

    @Autowired
    private RemuneracionesService remuneracionesService;

    /**
     * Endpoint para listar todos los empleados de la jerarquía como DTOs
     * @return Lista de DTOs con información básica de la persona más información específica
     */
    @GetMapping("/empleados")
    public List<EmpleadoDTO> listarTodosLosEmpleados() {
        return remuneracionesService.listarTodosLosEmpleados();
    }

    /**
     * Endpoint para calcular el total de remuneraciones de todos los empleados
     * @return Suma total de los salarios de todos los empleados
     */
    @GetMapping("/total-remuneraciones")
    public BigDecimal calcularTotalRemuneraciones() {
        return remuneracionesService.calcularTotalRemuneraciones();
    }

    /**
     * Endpoint para listar empleados DTO filtrados por tipo
     * @param tipoEmpleado Tipo de empleado ("tiempoCompleto", "porHora", "contratista")
     * @return Lista de DTOs de empleados del tipo especificado
     */
    @GetMapping("/empleados/tipo/{tipoEmpleado}")
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleadosPorTipo(@PathVariable String tipoEmpleado) {
        List<EmpleadoDTO> empleados = remuneracionesService.listarEmpleadosPorTipo(tipoEmpleado);
        return ResponseEntity.ok(empleados);
    }
}