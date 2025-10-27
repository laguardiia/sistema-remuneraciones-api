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

    @Override
    public BigDecimal calcularSalario() {
        // Calcular salario con descuento del 9%
        if (salarioMensual != null) {
            BigDecimal descuento = salarioMensual.multiply(BigDecimal.valueOf(0.09));
            return salarioMensual.subtract(descuento);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String obtenerInformacionCompleta() {
        // ✅ Sobrescribir para incluir propiedades específicas de EmpleadoTiempoCompleto
        return super.obtenerInformacionCompleta() +
                ", Departamento: " + departamento +
                ", Salario Bruto: " + salarioMensual +
                ", Salario con Descuento (9%): " + calcularSalario() +
                ", Deducciones (5%): " + calcularDeducciones() +
                ", Impuestos Base (10% sobre salario con descuento): " + calcularImpuestoBase() +
                ", Impuestos Totales: " + calcularImpuestos();
    }

    @Override
    public BigDecimal calcularDeducciones() {
        // ✅ 5% del salario si el departamento es "IT", 3% para otros
        if (salarioMensual != null) {
            if ("IT".equalsIgnoreCase(departamento)) {
                return salarioMensual.multiply(BigDecimal.valueOf(0.05));
            } else {
                return salarioMensual.multiply(BigDecimal.valueOf(0.03));
            }
        }
        return BigDecimal.ZERO;
    }

    @Override
    public boolean validarDatosEspecificos() {
        // Validar que el salario bruto sea mayor o igual a 2899048
        return this.salarioMensual != null &&
                this.salarioMensual.compareTo(BigDecimal.valueOf(2899048)) >= 0 &&
                this.departamento != null &&
                !this.departamento.trim().isEmpty();
    }
}