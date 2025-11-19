// src/main/java/py/edu/uc/lp32025/exception/GlobalExceptionHandler.java
package py.edu.uc.lp32025.exception;

import py.edu.uc.lp32025.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Manejar DiasInsuficientesException (Checked)
    @ExceptionHandler(DiasInsuficientesException.class)
    public ResponseEntity<ErrorDto> handleDiasInsuficientesException(DiasInsuficientesException ex) {
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(), // 400
                ex.getMessage(),
                "No se pueden solicitar los días solicitados: " + ex.getTipoSolicitud() + ". " + ex.getMessage() + ". Empleado: " + ex.getNombreEmpleado() + " " + ex.getApellidoEmpleado()
        );

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    // ✅ Manejar EmpleadoNoEncontradoException (Runtime)
    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<ErrorDto> handleEmpleadoNoEncontradoException(EmpleadoNoEncontradoException ex) {
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.NOT_FOUND.value(), // 404
                ex.getMessage(),
                "Empleado no encontrado. ID: " + ex.getNumeroEmpleado()
        );

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    // Manejar errores de validación de Bean Validation (@Pattern, @NotBlank, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> erroresDetallados = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            erroresDetallados.put(error.getField(), error.getDefaultMessage());
        }

        String mensajeErrorTecnico = "Errores de validación: " + erroresDetallados.toString();
        String mensajeErrorUsuario = "Error en la solicitud. Revise los campos enviados.";

        ErrorDto errorDto = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(), // 400
                mensajeErrorTecnico,
                mensajeErrorUsuario
        );

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    // Manejar errores de tipo de argumento (ej: edad no numérica)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(), // 400
                "Tipo de parámetro inválido para: " + ex.getName(),
                "El parámetro " + ex.getName() + " tiene un formato inválido."
        );

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    // Manejar excepciones personalizadas (FechaFuturaException)
    @ExceptionHandler(FechaFuturaException.class)
    public ResponseEntity<ErrorDto> handleFechaFuturaException(FechaFuturaException ex) {
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(), // 400
                ex.getMessage(),
                "La fecha de nacimiento no puede ser en el futuro."
        );

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    // Manejar excepciones de argumentos ilegales
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(), // 400
                ex.getMessage(),
                "Error en la solicitud: " + ex.getMessage()
        );

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    // Manejar excepciones generales (RuntimeException)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException ex) {
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Error interno del servidor: " + ex.getMessage(),
                "Ocurrió un error interno en el servidor."
        );

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Manejar excepciones generales (Exception)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneralException(Exception ex) {
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                ex.getClass().getSimpleName() + ": " + ex.getMessage(),
                "Error interno del servidor."
        );

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}