// src/main/java/py/edu/uc/lp32025/domain/Persona.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona implements Mapeable { // ✅ Implementa la interfaz

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    // ✅ Validación: número de documento debe tener entre 1 y 20 dígitos
    @Pattern(regexp = "^\\d{1,20}$", message = "El número de documento debe tener entre 1 y 20 dígitos")
    private String numeroDocumento;

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

    // ✅ Implementación MOCK de los métodos de Mapeable
    @Override
    public PosicionGps ubicarElemento() {
        // ✅ Retornar una posición MOCK basada en el ID o nombre
        // Ejemplo: Generar coordenadas MOCK pseudoaleatorias basadas en el ID
        double latBase = -25.2637; // Centro aproximado de Paraguay
        double lonBase = -57.5833;
        double offset = this.id != null ? this.id * 0.001 : 0.001; // Pequeño offset basado en ID

        return new PosicionGps(latBase + offset, lonBase + offset);
    }

    @Override
    public Avatar obtenerImagen() {
        // ✅ Retornar un Avatar MOCK basado en el nombre
        String nickMock = this.nombre != null ? this.nombre.substring(0, Math.min(this.nombre.length(), 10)) : "Anonimo";
        // En una implementación real, aquí cargarías la imagen desde un recurso o base de datos
        // Para MOCK, simplemente creamos un Avatar con un nick y una imagen nula
        return new Avatar(null, nickMock + "_" + (this.id != null ? this.id : "0"));
    }

    // ✅ 1. Método abstracto para calcular salario
    public abstract BigDecimal calcularSalario();

    // ✅ 2. Método concreto que puede ser sobrescrito
    public String obtenerInformacionCompleta() {
        return "Nombre: " + nombre + " " + apellido +
                ", Documento: " + numeroDocumento +
                ", Fecha Nacimiento: " + fechaNacimiento;
    }

    // ✅ 3. Método template para calcular impuestos
    public BigDecimal calcularImpuestos() {
        BigDecimal impuestoBase = calcularImpuestoBase();
        BigDecimal deducciones = calcularDeducciones();
        BigDecimal impuestoFinal = impuestoBase.subtract(deducciones);

        // Asegurar que no sea negativo
        return impuestoFinal.max(BigDecimal.ZERO);
    }

    // ✅ 3b. Método concreto para calcular impuesto base (10% del salario)
    public BigDecimal calcularImpuestoBase() {
        BigDecimal salario = calcularSalario();
        return salario.multiply(BigDecimal.valueOf(0.10));
    }

    // ✅ 3c. Método abstracto para calcular deducciones
    public abstract BigDecimal calcularDeducciones();

    // ✅ 4. Método abstracto para validar datos específicos
    public abstract boolean validarDatosEspecificos();
}