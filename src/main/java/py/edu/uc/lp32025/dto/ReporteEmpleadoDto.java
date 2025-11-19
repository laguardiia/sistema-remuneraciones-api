// src/main/java/py/edu/uc/lp32025/dto/ReporteEmpleadoDto.java
package py.edu.uc.lp32025.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReporteEmpleadoDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String tipoEmpleado; // "EmpleadoTiempoCompleto", "EmpleadoPorHora", "Contratista"
    private String informacionCompleta;
    private BigDecimal salario;
    private BigDecimal impuestos;
    private boolean datosValidos;
    private LocalDate fechaNacimiento;
    private String numeroDocumento;

    // Constructor vacío
    public ReporteEmpleadoDto() {
    }

    // Constructor con parámetros
    public ReporteEmpleadoDto(Long id, String nombre, String apellido, String tipoEmpleado,
                              String informacionCompleta, BigDecimal salario, BigDecimal impuestos,
                              boolean datosValidos, LocalDate fechaNacimiento, String numeroDocumento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoEmpleado = tipoEmpleado;
        this.informacionCompleta = informacionCompleta;
        this.salario = salario;
        this.impuestos = impuestos;
        this.datosValidos = datosValidos;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroDocumento = numeroDocumento;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public String getInformacionCompleta() {
        return informacionCompleta;
    }

    public void setInformacionCompleta(String informacionCompleta) {
        this.informacionCompleta = informacionCompleta;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public boolean isDatosValidos() {
        return datosValidos;
    }

    public void setDatosValidos(boolean datosValidos) {
        this.datosValidos = datosValidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    @Override
    public String toString() {
        return "ReporteEmpleadoDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", tipoEmpleado='" + tipoEmpleado + '\'' +
                ", informacionCompleta='" + informacionCompleta + '\'' +
                ", salario=" + salario +
                ", impuestos=" + impuestos +
                ", datosValidos=" + datosValidos +
                ", fechaNacimiento=" + fechaNacimiento +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                '}';
    }
}