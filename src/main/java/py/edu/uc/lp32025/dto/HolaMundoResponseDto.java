// src/main/java/py.edu.uc.lp32025/dto/HolaMundoResponseDto.java
package py.edu.uc.lp32025.dto;

public class HolaMundoResponseDto extends BaseResponseDto {

    private String mensaje;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String fechaHora;

    // Constructor vacío
    public HolaMundoResponseDto() {
        super(); // Llama al constructor de la clase base
    }

    // Constructor para respuestas exitosas
    public HolaMundoResponseDto(String mensaje, String nombre, String apellido, Integer edad, String fechaHora) {
        super(200, null, null); // Código 200 = OK
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fechaHora = fechaHora;
    }

    // Constructor para errores
    public HolaMundoResponseDto(int codigoEstado, String mensajeErrorTecnico, String mensajeErrorUsuario) {
        super(codigoEstado, mensajeErrorTecnico, mensajeErrorUsuario);
    }

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "HolaMundoResponseDto{" +
                "mensaje='" + mensaje + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", fechaHora='" + fechaHora + '\'' +
                "} " + super.toString();
    }
}