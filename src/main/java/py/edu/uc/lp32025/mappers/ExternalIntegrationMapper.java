// src/main/java/py/edu/uc/lp32025/mappers/ExternalIntegrationMapper.java
package py.edu.uc.lp32025.mappers;

import org.springframework.stereotype.Component;
import py.edu.uc.lp32025.dto.EmpleadoDTO;
import py.edu.uc.lp32025.dto.ExternalPersonaDto;

@Component
public class ExternalIntegrationMapper extends AbstractBaseMapper<ExternalPersonaDto, EmpleadoDTO> {

    @Override
    public EmpleadoDTO mapToDto(ExternalPersonaDto input) {
        if (input == null) return null;

        // Lógica de transformación
        return EmpleadoDTO.builder()
                .nombre(input.getFirst_name_val())
                .apellido(input.getLast_name_val())
                // CORREGIDO: Mapeamos el ID externo al numeroDocumento
                .numeroDocumento(input.getExt_id_ref())
                // El email se ignora porque EmpleadoDTO no tiene campo email
                .tipoEmpleado("Externo")
                .build();
    }

    @Override
    public ExternalPersonaDto mapToEntity(EmpleadoDTO output) {
        if (output == null) return null;

        ExternalPersonaDto ext = new ExternalPersonaDto();
        ext.setFirst_name_val(output.getNombre());
        ext.setLast_name_val(output.getApellido());
        ext.setExt_id_ref(output.getNumeroDocumento());
        // ext.setContact_email(...) // Eliminado porque output no tiene getEmail()

        return ext;
    }
}