// src/main/java/py/edu/uc/lp32025/domain/EmpleadoTiempoCompleto.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import py.edu.uc.lp32025.exception.PermisoDenegadoException;
import com.fasterxml.jackson.annotation.JsonIgnore; // Importante para evitar ciclos infinitos en JSON

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "empleados_tiempo_completo")
public class EmpleadoTiempoCompleto extends Persona implements Permisionable {

    @Column(name = "salario_mensual", precision = 10, scale = 2, nullable = false)
    private BigDecimal salarioMensual;

    @Column(name = "departamento", nullable = false)
    private String departamento;

    @Column(name = "numero_empleado", nullable = false, unique = true)
    private String numeroEmpleado;

    // ✅ Relación agregada para solucionar el error en DemoPermisionable y JPA
    @ManyToOne
    @JoinColumn(name = "gerente_id")
    @JsonIgnore // Evita que al pedir un empleado se traiga al gerente y se haga un bucle infinito
    private Gerente gerente;

    // Getters y Setters
    public BigDecimal getSalarioMensual() {
        return salarioMensual;
    }

    public void setSalarioMensual(BigDecimal salarioMensual) {
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

        long dias = java.time.temporal.ChronoUnit.DAYS.between(inicio, fin) + 1;
        // Validación simple: mínimo 1 día
        if (dias < 1) {
            throw new PermisoDenegadoException("Duración inválida", "Mínimo 1 día", "VACACIONES", getNombre(), getApellido(), getNumeroEmpleado());
        }
        System.out.println("Vacaciones solicitadas por " + getNombre());
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
            return salarioMensual.multiply(BigDecimal.valueOf(0.09)); // 9% IPS ejemplo
        }
        return BigDecimal.ZERO;
    }

    @Override
    public boolean validarDatosEspecificos() {
        return this.salarioMensual != null && this.departamento != null;
    }
}