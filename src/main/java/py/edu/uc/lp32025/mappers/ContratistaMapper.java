// src/main/java/py/edu/uc/lp32025/mappers/ContratistaMapper.java
package py.edu.uc.lp32025.mappers;

import org.springframework.stereotype.Component;
import py.edu.uc.lp32025.domain.Contratista;
import py.edu.uc.lp32025.dto.EmpleadoDTO;

@Component
public class ContratistaMapper extends AbstractBaseMapper<Contratista, EmpleadoDTO> {

    @Override
    public EmpleadoDTO mapToDto(Contratista entity) {
        if (entity == null) return null;

        return EmpleadoDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .fechaNacimiento(entity.getFechaNacimiento())
                .numeroDocumento(entity.getNumeroDocumento())
                .numeroEmpleado(entity.getNumeroEmpleado())
                .tipoEmpleado("Contratista")
                .salario(entity.calcularSalario())
                .informacionEspecifica("Fin Contrato: " + entity.getFechaFinContrato())
                .build();
    }

    @Override
    public Contratista mapToEntity(EmpleadoDTO dto) {
        if (dto == null) return null;
        Contratista entity = new Contratista();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        return entity;
    }
}