// src/main/java/py/edu/uc/lp32025/domain/EmpleadoPorHoras.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "empleados_por_hora")
public class EmpleadoPorHora extends Persona {

    @Column(name = "tarifa_por_hora", precision = 8, scale = 2, nullable = false)
    private BigDecimal tarifaPorHora;

    @Column(name = "horas_trabajadas", nullable = false)
    private Integer horasTrabajadas;

    // Getters y Setters
    public BigDecimal getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(BigDecimal tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
    }

    public Integer getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Integer horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    @Override
    public BigDecimal calcularSalario() {
        // ✅ Tarifa × horas + bonus del 50% de la tarifa por horas extra (>40h)
        if (tarifaPorHora != null && horasTrabajadas != null) {
            BigDecimal salarioBase = tarifaPorHora.multiply(BigDecimal.valueOf(horasTrabajadas));

            // Calcular horas extra (>40h)
            if (horasTrabajadas > 40) {
                int horasExtra = horasTrabajadas - 40;
                BigDecimal bonusHorasExtra = tarifaPorHora.multiply(BigDecimal.valueOf(0.5)) // 50% de la tarifa
                        .multiply(BigDecimal.valueOf(horasExtra));
                return salarioBase.add(bonusHorasExtra);
            }

            return salarioBase;
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String obtenerInformacionCompleta() {
        return super.obtenerInformacionCompleta() +
                ", Tarifa/Hora: " + tarifaPorHora +
                ", Horas: " + horasTrabajadas +
                ", Salario: " + calcularSalario();
    }

    @Override
    public BigDecimal calcularDeducciones() {
        // ✅ 2% del salario total
        BigDecimal salarioTotal = calcularSalario();
        return salarioTotal.multiply(BigDecimal.valueOf(0.02));
    }

    @Override
    public boolean validarDatosEspecificos() {
        // ✅ Tarifa > 0, horas entre 1 y 80
        return this.tarifaPorHora != null &&
                this.tarifaPorHora.compareTo(BigDecimal.ZERO) > 0 &&
                this.horasTrabajadas != null &&
                this.horasTrabajadas >= 1 &&
                this.horasTrabajadas <= 80;
    }
}