// src/main/java/py/edu/uc/lp32025/exception/GlobalExceptionHandler.java
package py.edu.uc.lp32025.exception;

import py.edu.uc.lp32025.dto.BaseResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Manejar errores de validación de Bean Validation (@Pattern, @NotBlank, etc.) - Request Body
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BaseResponseDto errorResponse = new BaseResponseDto();
        errorResponse.setCodigoEstado(400);

        // Construir mensaje detallado de errores
        StringBuilder mensajeErrorTecnico = new StringBuilder("Errores de validación: ");
        StringBuilder mensajeErrorUsuario = new StringBuilder("Errores de validación: ");
        StringBuilder mensaje = new StringBuilder("Errores de validación: ");

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String detalle = String.format("Campo '%s' con valor '%s': %s",
                    error.getField(),
                    error.getRejectedValue() != null ? error.getRejectedValue().toString() : "null",
                    error.getDefaultMessage());

            mensajeErrorTecnico.append(detalle).append("; ");
            mensajeErrorUsuario.append(detalle).append("; ");
            mensaje.append(detalle).append("; ");
        }

        errorResponse.setMensajeErrorTecnico(mensajeErrorTecnico.toString());
        errorResponse.setMensajeErrorUsuario(mensajeErrorUsuario.toString());
        errorResponse.setMensaje(mensaje.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // ✅ Manejar errores de validación de Bean Validation - Persistencia (ConstraintViolationException)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponseDto> handleConstraintViolationException(ConstraintViolationException ex) {
        BaseResponseDto errorResponse = new BaseResponseDto();
        errorResponse.setCodigoEstado(400);

        // Extraer los mensajes de violación de constraint
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        StringBuilder mensajeErrorTecnico = new StringBuilder("Errores de validación en persistencia: ");
        StringBuilder mensajeErrorUsuario = new StringBuilder("Errores de validación: ");
        StringBuilder mensaje = new StringBuilder("Errores de validación en persistencia: ");

        for (ConstraintViolation<?> violation : constraintViolations) {
            String propiedad = violation.getPropertyPath().toString();
            String valor = violation.getInvalidValue() != null ? violation.getInvalidValue().toString() : "null";
            String mensajeError = violation.getMessage();

            String detalle = String.format("Campo '%s' con valor '%s': %s", propiedad, valor, mensajeError);

            mensajeErrorTecnico.append(detalle).append("; ");
            mensajeErrorUsuario.append(detalle).append("; ");
            mensaje.append(detalle).append("; ");
        }

        errorResponse.setMensajeErrorTecnico(mensajeErrorTecnico.toString());
        errorResponse.setMensajeErrorUsuario(mensajeErrorUsuario.toString());
        errorResponse.setMensaje(mensaje.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Manejar errores de tipo de argumento (ej: edad no numérica)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        BaseResponseDto errorResponse = new BaseResponseDto();
        errorResponse.setCodigoEstado(400);
        errorResponse.setMensajeErrorTecnico("Tipo de parámetro inválido para: " + ex.getName());
        errorResponse.setMensajeErrorUsuario("El parámetro " + ex.getName() + " tiene un formato inválido");
        errorResponse.setMensaje("Error de validación en parámetro: " + ex.getName());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Manejar excepciones personalizadas
    @ExceptionHandler(FechaFuturaException.class)
    public ResponseEntity<BaseResponseDto> handleFechaFuturaException(FechaFuturaException ex) {
        BaseResponseDto errorResponse = new BaseResponseDto();
        errorResponse.setCodigoEstado(400);
        errorResponse.setMensajeErrorTecnico(ex.getMessage());
        errorResponse.setMensajeErrorUsuario("La fecha de nacimiento no puede ser en el futuro");
        errorResponse.setMensaje("Error: " + ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Manejar errores de argumentos ilegales
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        BaseResponseDto errorResponse = new BaseResponseDto();
        errorResponse.setCodigoEstado(400);
        errorResponse.setMensajeErrorTecnico(ex.getMessage());
        errorResponse.setMensajeErrorUsuario(ex.getMessage());
        errorResponse.setMensaje("Error de validación: " + ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Manejar errores de integridad de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponseDto> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        BaseResponseDto errorResponse = new BaseResponseDto();
        errorResponse.setCodigoEstado(400);
        errorResponse.setMensajeErrorTecnico("Error de integridad de datos: " + ex.getMostSpecificCause().getMessage());
        errorResponse.setMensajeErrorUsuario("Error de integridad de datos. Verifique los datos ingresados.");
        errorResponse.setMensaje("Error de integridad de datos: " + ex.getMostSpecificCause().getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Manejar excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDto> handleGeneralException(Exception ex) {
        BaseResponseDto errorResponse = new BaseResponseDto();
        errorResponse.setCodigoEstado(500);
        errorResponse.setMensajeErrorTecnico(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        errorResponse.setMensajeErrorUsuario("Error interno en el servidor");
        errorResponse.setMensaje("Error interno del servidor: " + ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}