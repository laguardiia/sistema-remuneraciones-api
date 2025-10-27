// src/main/java/py/edu/uc/lp32025/controller/EmpleadoTiempoCompletoController.java
package py.edu.uc.lp32025.controller;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.dto.BatchResponse;
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

    @GetMapping("/{id}/impuestos")
    public ResponseEntity<ImpuestosResponse> consultarImpuestos(@PathVariable Long id) {
        EmpleadoTiempoCompleto empleado = empleadoService.obtenerDatosImpuestos(id);
        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            BigDecimal salarioBruto = empleado.getSalarioMensual();
            BigDecimal salarioConDescuento = empleado.calcularSalario();
            BigDecimal deducciones = empleado.calcularDeducciones();
            BigDecimal impuestoBase = empleado.calcularImpuestoBase();
            BigDecimal impuestosTotales = empleado.calcularImpuestos();

            ImpuestosResponse response = new ImpuestosResponse(
                    id,
                    empleado.getNombre() + " " + empleado.getApellido(),
                    empleado.getDepartamento(),
                    salarioBruto,
                    salarioConDescuento,
                    deducciones,
                    impuestoBase,
                    impuestosTotales
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ✅ Nuevo endpoint para guardar empleados en batch
    @PostMapping("/batch")
    public ResponseEntity<BatchResponse> guardarEmpleadosEnBatch(@RequestBody List<EmpleadoTiempoCompleto> empleados) {
        try {
            BatchResponse response = empleadoService.guardarEmpleadosEnBatch(empleados);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

// ✅ Clase DTO para la respuesta de impuestos
class ImpuestosResponse {
    private Long id;
    private String nombreCompleto;
    private String departamento;
    private BigDecimal salarioBruto;
    private BigDecimal salarioConDescuento;
    private BigDecimal deducciones;
    private BigDecimal impuestoBase;
    private BigDecimal impuestosTotales;

    public ImpuestosResponse(Long id, String nombreCompleto, String departamento,
                             BigDecimal salarioBruto, BigDecimal salarioConDescuento,
                             BigDecimal deducciones, BigDecimal impuestoBase,
                             BigDecimal impuestosTotales) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.departamento = departamento;
        this.salarioBruto = salarioBruto;
        this.salarioConDescuento = salarioConDescuento;
        this.deducciones = deducciones;
        this.impuestoBase = impuestoBase;
        this.impuestosTotales = impuestosTotales;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public BigDecimal getSalarioBruto() {
        return salarioBruto;
    }

    public void setSalarioBruto(BigDecimal salarioBruto) {
        this.salarioBruto = salarioBruto;
    }

    public BigDecimal getSalarioConDescuento() {
        return salarioConDescuento;
    }

    public void setSalarioConDescuento(BigDecimal salarioConDescuento) {
        this.salarioConDescuento = salarioConDescuento;
    }

    public BigDecimal getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(BigDecimal deducciones) {
        this.deducciones = deducciones;
    }

    public BigDecimal getImpuestoBase() {
        return impuestoBase;
    }

    public void setImpuestoBase(BigDecimal impuestoBase) {
        this.impuestoBase = impuestoBase;
    }

    public BigDecimal getImpuestosTotales() {
        return impuestosTotales;
    }

    public void setImpuestosTotales(BigDecimal impuestosTotales) {
        this.impuestosTotales = impuestosTotales;
    }
}