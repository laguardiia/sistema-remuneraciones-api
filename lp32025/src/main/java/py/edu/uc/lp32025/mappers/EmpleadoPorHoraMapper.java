// src/main/java/py/edu/uc/lp32025/mappers/EmpleadoPorHoraMapper.java
package py.edu.uc.lp32025.mappers;

import org.springframework.stereotype.Component;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;
import py.edu.uc.lp32025.dto.EmpleadoDTO;

@Component
public class EmpleadoPorHoraMapper extends AbstractBaseMapper<EmpleadoPorHora, EmpleadoDTO> {

    @Override
    public EmpleadoDTO mapToDto(EmpleadoPorHora entity) {
        if (entity == null) return null;

        return EmpleadoDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .fechaNacimiento(entity.getFechaNacimiento())
                .numeroDocumento(entity.getNumeroDocumento())
                .numeroEmpleado(entity.getNumeroEmpleado())
                .tipoEmpleado("EmpleadoPorHora")
                .salario(entity.calcularSalario())
                .informacionEspecifica("Horas: " + entity.getHorasTrabajadas() + ", Tarifa: " + entity.getTarifaPorHora())
                .build();
    }

    @Override
    public EmpleadoPorHora mapToEntity(EmpleadoDTO dto) {
        if (dto == null) return null;
        EmpleadoPorHora entity = new EmpleadoPorHora();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        // Mapeo básico inverso
        return entity;
    }
}