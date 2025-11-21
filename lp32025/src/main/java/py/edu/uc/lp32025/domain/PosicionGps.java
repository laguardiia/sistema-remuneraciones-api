// src/main/java/py/edu/uc/lp32025/domain/PosicionGps.java
package py.edu.uc.lp32025.domain;

public class PosicionGps {
    private double latitud;
    private double longitud;

    // Constructor vacío
    public PosicionGps() {
    }

    // Constructor con parámetros
    public PosicionGps(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y Setters
    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "PosicionGps{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}