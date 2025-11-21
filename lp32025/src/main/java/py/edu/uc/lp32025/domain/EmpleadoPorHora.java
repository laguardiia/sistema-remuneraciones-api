// src/main/java/py/edu/uc/lp32025/domain/EmpleadoPorHora.java
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

    @Column(name = "numero_empleado", nullable = false, unique = true)
    private String numeroEmpleado;

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

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    @Override
    public BigDecimal calcularSalario() {
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
        BigDecimal salarioTotal = calcularSalario();
        return salarioTotal.multiply(BigDecimal.valueOf(0.02));
    }

    @Override
    public boolean validarDatosEspecificos() {
        return this.tarifaPorHora != null &&
                this.tarifaPorHora.compareTo(BigDecimal.ZERO) > 0 &&
                this.horasTrabajadas != null &&
                this.horasTrabajadas >= 1 &&
                this.horasTrabajadas <= 80;
    }

    // Implementación de Mapeable
    @Override
    public PosicionGps ubicarElemento() {
        PosicionGps base = super.ubicarElemento();
        double offsetHoras = (this.horasTrabajadas != null ? this.horasTrabajadas : 0) * 0.0001;
        return new PosicionGps(base.getLatitud() + offsetHoras, base.getLongitud() + offsetHoras);
    }

    @Override
    public Avatar obtenerImagen() {
        Avatar base = super.obtenerImagen();
        return new Avatar(null, "EMP_PH_" + base.getNick());
    }
}