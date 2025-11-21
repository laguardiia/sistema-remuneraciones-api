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

    @Column(name = "numero_empleado", nullable = false, unique = true)
    private String numeroEmpleado;

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

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    @Override
    public BigDecimal calcularSalario() {
        if (montoPorProyecto != null && proyectosCompletados != null) {
            return montoPorProyecto.multiply(BigDecimal.valueOf(proyectosCompletados));
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String obtenerInformacionCompleta() {
        return super.obtenerInformacionCompleta() +
                ", Proyectos: " + proyectosCompletados +
                ", Monto/Proyecto: " + montoPorProyecto +
                ", Salario Total: " + calcularSalario() +
                ", Fecha Fin Contrato: " + fechaFinContrato +
                ", Contrato Vigente: " + contratoVigente();
    }

    @Override
    public BigDecimal calcularDeducciones() {
        return BigDecimal.ZERO;
    }

    @Override
    public boolean validarDatosEspecificos() {
        return this.fechaFinContrato != null &&
                this.fechaFinContrato.isAfter(LocalDate.now()) &&
                this.proyectosCompletados != null &&
                this.proyectosCompletados >= 0 &&
                this.montoPorProyecto != null &&
                this.montoPorProyecto.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean contratoVigente() {
        if (fechaFinContrato != null) {
            return fechaFinContrato.isAfter(LocalDate.now());
        }
        return false;
    }

    // Implementación de Mapeable
    @Override
    public PosicionGps ubicarElemento() {
        PosicionGps base = super.ubicarElemento();
        double offsetProyectos = (this.proyectosCompletados != null ? this.proyectosCompletados : 0) * 0.00001;
        return new PosicionGps(base.getLatitud() + offsetProyectos, base.getLongitud() + offsetProyectos);
    }

    @Override
    public Avatar obtenerImagen() {
        Avatar base = super.obtenerImagen();
        return new Avatar(null, "CONTR_" + base.getNick());
    }
}