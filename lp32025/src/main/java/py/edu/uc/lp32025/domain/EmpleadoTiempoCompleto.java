// src/main/java/py/edu/uc/lp32025/domain/EmpleadoTiempoCompleto.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "empleados_tiempo_completo")
public class EmpleadoTiempoCompleto extends Persona {

    @Column(name = "salario_mensual", precision = 10, scale = 2, nullable = false)
    private BigDecimal salarioMensual;

    @Column(name = "departamento", nullable = false)
    private String departamento;

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
}