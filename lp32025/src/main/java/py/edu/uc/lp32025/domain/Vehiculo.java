// src/main/java/py/edu/uc/lp32025/domain/Vehiculo.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculos")
public class Vehiculo implements Mapeable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private String placa;
    private Double latitud; // Coordenada para ubicación MOCK
    private Double longitud; // Coordenada para ubicación MOCK
    private String tipo; // Por ejemplo: "auto", "moto", "camioneta"

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // ✅ Implementación MOCK de los métodos de Mapeable
    @Override
    public PosicionGps ubicarElemento() {
        // Usar coordenadas específicas del vehículo si están definidas, sino MOCK general
        if (latitud != null && longitud != null) {
            return new PosicionGps(latitud, longitud);
        }
        // Generar coordenadas MOCK basadas en el ID
        double latBase = -25.2637; // Centro aproximado de Paraguay
        double lonBase = -57.5833;
        double offset = this.id != null ? this.id * 0.001 : 0.001;
        return new PosicionGps(latBase + offset, lonBase + offset);
    }

    @Override
    public Avatar obtenerImagen() {
        // Crear un avatar MOCK basado en la placa o modelo
        String nickMock = this.placa != null ? this.placa : this.modelo;
        nickMock = nickMock != null ? nickMock.substring(0, Math.min(nickMock.length(), 10)) : "VehiculoAnonimo";
        return new Avatar(null, "VEH_" + nickMock + "_" + (this.id != null ? this.id : "0"));
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}