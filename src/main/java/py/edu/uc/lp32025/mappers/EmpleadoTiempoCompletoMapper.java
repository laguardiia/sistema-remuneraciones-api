// src/main/java/py/edu/uc/lp32025/mappers/EmpleadoTiempoCompletoMapper.java
package py.edu.uc.lp32025.mappers;

import org.springframework.stereotype.Component;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.dto.EmpleadoDTO;

@Component
public class EmpleadoTiempoCompletoMapper extends AbstractBaseMapper<EmpleadoTiempoCompleto, EmpleadoDTO> {

    @Override
    public EmpleadoDTO mapToDto(EmpleadoTiempoCompleto entity) {
        if (entity == null) return null;

        return EmpleadoDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .fechaNacimiento(entity.getFechaNacimiento())
                .numeroDocumento(entity.getNumeroDocumento())
                .numeroEmpleado(entity.getNumeroEmpleado())
                .tipoEmpleado("EmpleadoTiempoCompleto")
                .salario(entity.calcularSalario())
                // ✅ Mapeamos el saldo disponible
                .diasVacacionesDisponibles(entity.getDiasVacacionesDisponibles())
                .build();
    }

    @Override
    public EmpleadoTiempoCompleto mapToEntity(EmpleadoDTO dto) {
        if (dto == null) return null;

        EmpleadoTiempoCompleto entity = new EmpleadoTiempoCompleto();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setFechaNacimiento(dto.getFechaNacimiento());
        entity.setNumeroDocumento(dto.getNumeroDocumento());
        entity.setNumeroEmpleado(dto.getNumeroEmpleado());

        // Si el DTO trae saldo, lo seteamos, sino usa el default (30)
        if (dto.getDiasVacacionesDisponibles() != null) {
            entity.setDiasVacacionesDisponibles(dto.getDiasVacacionesDisponibles());
        }

        return entity;
    }
}