// src/main/java/py/edu/uc/lp32025/domain/EmpleadoTiempoCompleto.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min; // Importante
import jakarta.validation.constraints.NotNull;
import py.edu.uc.lp32025.exception.PermisoDenegadoException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "empleados_tiempo_completo")
public class EmpleadoTiempoCompleto extends Persona implements Permisionable {

    // ✅ LÓGICA MODIFICADA: Validación de Sueldo Mínimo
    // Establecemos el mínimo legal vigente (aprox 2.798.309 Gs)
    @NotNull(message = "El salario mensual es obligatorio")
    @Min(value = 2798309, message = "El salario no puede ser inferior al sueldo mínimo legal (2.798.309 Gs)")
    @Column(name = "salario_mensual", precision = 10, scale = 2, nullable = false)
    private BigDecimal salarioMensual;

    @Column(name = "departamento", nullable = false)
    private String departamento;

    @Column(name = "numero_empleado", nullable = false, unique = true)
    private String numeroEmpleado;

    @Column(name = "dias_vacaciones_disponibles")
    private Integer diasVacacionesDisponibles = 30;

    @ManyToOne
    @JoinColumn(name = "gerente_id")
    @JsonIgnore
    private Gerente gerente;

    // Getters y Setters
    public BigDecimal getSalarioMensual() {
        return salarioMensual;
    }

    // ✅ Validación manual en el setter por si se modifica post-creación
    public void setSalarioMensual(BigDecimal salarioMensual) {
        BigDecimal sueldoMinimo = new BigDecimal("2798309");
        if (salarioMensual != null && salarioMensual.compareTo(sueldoMinimo) < 0) {
            throw new IllegalArgumentException("El salario (" + salarioMensual + ") no cumple con el sueldo mínimo legal: " + sueldoMinimo);
        }
        this.salarioMensual = salarioMensual;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getNumeroEmpleado() {
        return this.numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public Gerente getGerente() {
        return gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    public Integer getDiasVacacionesDisponibles() {
        return diasVacacionesDisponibles;
    }

    public void setDiasVacacionesDisponibles(Integer diasVacacionesDisponibles) {
        this.diasVacacionesDisponibles = diasVacacionesDisponibles;
    }

    @Override
    public String getNombreEmpleado() {
        return this.getNombre();
    }

    @Override
    public String getApellidoEmpleado() {
        return this.getApellido();
    }

    @Override
    public void solicitarVacaciones(LocalDate[] rangoFechas) throws PermisoDenegadoException {
        if (rangoFechas == null || rangoFechas.length != 2) {
            throw new PermisoDenegadoException("Rango inválido", "Faltan fechas", "VACACIONES", getNombre(), getApellido(), getNumeroEmpleado());
        }
        LocalDate inicio = rangoFechas[0];
        LocalDate fin = rangoFechas[1];

        long diasSolicitadosLong = ChronoUnit.DAYS.between(inicio, fin) + 1;
        int diasSolicitados = (int) diasSolicitadosLong;

        if (diasSolicitados < 1) {
            throw new PermisoDenegadoException("Duración inválida", "Mínimo 1 día", "VACACIONES", getNombre(), getApellido(), getNumeroEmpleado());
        }

        if (diasSolicitados > this.diasVacacionesDisponibles) {
            throw new PermisoDenegadoException(
                    "Saldo Insuficiente",
                    "Solo le quedan " + this.diasVacacionesDisponibles + " días disponibles. Solicitó: " + diasSolicitados,
                    "VACACIONES",
                    getNombre(),
                    getApellido(),
                    getNumeroEmpleado()
            );
        }

        this.diasVacacionesDisponibles = this.diasVacacionesDisponibles - diasSolicitados;
        System.out.println("Vacaciones aprobadas para " + getNombre() + ". Días descontados: " + diasSolicitados + ". Nuevo saldo: " + this.diasVacacionesDisponibles);
    }

    @Override
    public void solicitarPermiso(String tipo, LocalDate[] rangoFechas, String justificacion) throws PermisoDenegadoException {
        System.out.println("Permiso solicitado por " + getNombre());
    }

    @Override
    public BigDecimal calcularSalario() {
        return this.salarioMensual != null ? this.salarioMensual : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calcularDeducciones() {
        if (salarioMensual != null) {
            return salarioMensual.multiply(BigDecimal.valueOf(0.09));
        }
        return BigDecimal.ZERO;
    }

    @Override
    public boolean validarDatosEspecificos() {
        return this.salarioMensual != null && this.departamento != null;
    }
}