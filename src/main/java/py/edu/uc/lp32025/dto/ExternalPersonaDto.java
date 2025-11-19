// src/main/java/py/edu/uc/lp32025/dto/ExternalPersonaDto.java
package py.edu.uc.lp32025.dto;

import lombok.Data;

@Data
public class ExternalPersonaDto {
    // Simula un JSON externo con estructura diferente
    private String ext_id_ref;
    private String first_name_val;
    private String last_name_val;
    private String contact_email;
    private Double salary_base;
}