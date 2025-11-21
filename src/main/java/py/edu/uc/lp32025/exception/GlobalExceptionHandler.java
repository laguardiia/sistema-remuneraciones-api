// src/main/java/py/edu/uc/lp32025/exception/GlobalExceptionHandler.java
package py.edu.uc.lp32025.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import py.edu.uc.lp32025.dto.ErrorDto;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de Fecha Futura (Validación personalizada)
    @ExceptionHandler(FechaFuturaException.class)
    public ResponseEntity<ErrorDto> handleFechaFuturaException(FechaFuturaException ex) {
        ErrorDto error = ErrorDto.builder()
                .codigoEstado(HttpStatus.BAD_REQUEST.value())
                .mensaje(ex.getMessage())
                .mensajeUsuario("La fecha ingresada no es válida (está en el futuro).")
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Manejo de Validaciones de Spring (@Min, @NotNull, @Past)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String mensajesError = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ErrorDto error = ErrorDto.builder()
                .codigoEstado(HttpStatus.BAD_REQUEST.value())
                .mensaje("Error de validación de datos")
                .mensajeUsuario(mensajesError)
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DiasInsuficientesException.class)
    public ResponseEntity<ErrorDto> handleDiasInsuficientesException(DiasInsuficientesException ex) {
        ErrorDto error = ErrorDto.builder()
                .codigoEstado(HttpStatus.BAD_REQUEST.value())
                .mensaje(ex.getMessage())
                .mensajeUsuario("No tiene suficientes días disponibles para esta solicitud.")
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<ErrorDto> handleEmpleadoNoEncontradoException(EmpleadoNoEncontradoException ex) {
        ErrorDto error = ErrorDto.builder()
                .codigoEstado(HttpStatus.NOT_FOUND.value())
                .mensaje(ex.getMessage())
                .mensajeUsuario("El empleado solicitado no existe en el sistema.")
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
        ErrorDto error = ErrorDto.builder()
                .codigoEstado(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .mensaje("Error interno del servidor: " + ex.getMessage())
                .mensajeUsuario("Ocurrió un error interno en el servidor.")
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}