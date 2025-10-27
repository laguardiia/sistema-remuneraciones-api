// src/main/java/py/edu/uc/lp32025/domain/Contratista.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contratistas")
public class Contratista extends Persona {

    @Column(name = "monto_por_proyecto", precision = 10, scale = 2, nullable = false)
    private BigDecimal montoPorProyecto;

    @Column(name = "proyectos_completados", nullable = false)
    private Integer proyectosCompletados;

    @Column(name = "fecha_fin_contrato", nullable = false)
    private LocalDate fechaFinContrato;

    // Getters y Setters
    public BigDecimal getMontoPorProyecto() {
        return montoPorProyecto;
    }

    public void setMontoPorProyecto(BigDecimal montoPorProyecto) {
        this.montoPorProyecto = montoPorProyecto;
    }

    public Integer getProyectosCompletados() {
        return proyectosCompletados;
    }

    public void setProyectosCompletados(Integer proyectosCompletados) {
        this.proyectosCompletados = proyectosCompletados;
    }

    public LocalDate getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(LocalDate fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
    }

    @Override
    public BigDecimal calcularSalario() {
        // ✅ Monto × número de proyectos
        if (montoPorProyecto != null && proyectosCompletados != null) {
            return montoPorProyecto.multiply(BigDecimal.valueOf(proyectosCompletados));
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String obtenerInformacionCompleta() {
        // ✅ Incluye fecha de vencimiento del contrato
        return super.obtenerInformacionCompleta() +
                ", Proyectos: " + proyectosCompletados +
                ", Monto/Proyecto: " + montoPorProyecto +
                ", Salario Total: " + calcularSalario() +
                ", Fecha Fin Contrato: " + fechaFinContrato +
                ", Contrato Vigente: " + contratoVigente();
    }

    @Override
    public BigDecimal calcularDeducciones() {
        // ✅ Sin deducciones (retorna 0)
        return BigDecimal.ZERO;
    }

    @Override
    public boolean validarDatosEspecificos() {
        // ✅ Fecha de fin debe ser futura, proyectos ≥ 0
        return this.fechaFinContrato != null &&
                this.fechaFinContrato.isAfter(LocalDate.now()) &&
                this.proyectosCompletados != null &&
                this.proyectosCompletados >= 0 &&
                this.montoPorProyecto != null &&
                this.montoPorProyecto.compareTo(BigDecimal.ZERO) > 0;
    }

    // ✅ Método contratoVigente() que verifique si el contrato no ha vencido
    public boolean contratoVigente() {
        if (fechaFinContrato != null) {
            return fechaFinContrato.isAfter(LocalDate.now());
        }
        return false;
    }
}