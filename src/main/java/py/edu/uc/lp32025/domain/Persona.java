// src/main/java/py/edu/uc/lp32025/domain/Persona.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import py.edu.uc.lp32025.exception.FechaFuturaException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona implements Mapeable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull(message = "El apellido no puede ser nulo")
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "email")
    private String email;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "numero_documento", unique = true)
    private String numeroDocumento;

    // Constructor vacío
    public Persona() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento != null && fechaNacimiento.isAfter(LocalDate.now())) {
            throw new FechaFuturaException("La fecha de nacimiento no puede ser futura: " + fechaNacimiento);
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    // Métodos abstractos
    public abstract BigDecimal calcularSalario();
    public abstract BigDecimal calcularDeducciones();
    public abstract boolean validarDatosEspecificos();

    // Métodos comunes
    public final BigDecimal calcularImpuestos() {
        BigDecimal impuestoBase = calcularImpuestoBase();
        BigDecimal deducciones = calcularDeducciones();
        return impuestoBase.subtract(deducciones).max(BigDecimal.ZERO);
    }

    public BigDecimal calcularImpuestoBase() {
        return calcularSalario().multiply(BigDecimal.valueOf(0.10));
    }

    // ✅ Método agregado para solucionar el error de override en subclases
    public String obtenerInformacionCompleta() {
        return "ID: " + id + ", Nombre: " + nombre + " " + apellido + ", Doc: " + numeroDocumento;
    }

    // Implementación Mapeable
    @Override
    public PosicionGps ubicarElemento() {
        return new PosicionGps(-25.2637, -57.5759);
    }

    @Override
    public Avatar obtenerImagen() {
        return new Avatar("https://ui-avatars.com/api/?name=" + this.nombre + "+" + this.apellido);
    }
}