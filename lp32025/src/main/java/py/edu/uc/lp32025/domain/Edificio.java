// src/main/java/py/edu/uc/lp32025/domain/Edificio.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "edificios")
public class Edificio implements Mapeable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private Double latitud; // Coordenada para ubicación MOCK
    private Double longitud; // Coordenada para ubicación MOCK
    private Integer pisos; // Número de pisos del edificio

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Integer getPisos() {
        return pisos;
    }

    public void setPisos(Integer pisos) {
        this.pisos = pisos;
    }

    // ✅ Implementación MOCK de los métodos de Mapeable
    @Override
    public PosicionGps ubicarElemento() {
        // Usar coordenadas específicas del edificio si están definidas, sino MOCK general
        if (latitud != null && longitud != null) {
            return new PosicionGps(latitud, longitud);
        }
        // Generar coordenadas MOCK basadas en el ID
        double latBase = -25.2637; // Centro aproximado de Paraguay
        double lonBase = -57.5833;
        double offset = this.id != null ? this.id * 0.002 : 0.002; // Offset ligeramente diferente al de vehículos
        return new PosicionGps(latBase + offset, lonBase + offset);
    }

    @Override
    public Avatar obtenerImagen() {
        // Crear un avatar MOCK basado en el nombre
        String nickMock = this.nombre != null ? this.nombre.substring(0, Math.min(this.nombre.length(), 10)) : "EdificioAnonimo";
        return new Avatar(null, "EDIF_" + nickMock + "_" + (this.id != null ? this.id : "0"));
    }

    @Override
    public String toString() {
        return "Edificio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", pisos=" + pisos +
                '}';
    }
}