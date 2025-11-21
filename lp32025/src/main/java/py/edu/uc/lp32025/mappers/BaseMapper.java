// src/main/java/py/edu/uc/lp32025/mappers/BaseMapper.java
package py.edu.uc.lp32025.mappers;

import java.util.List;

public interface BaseMapper<I, O> {
    O mapToDto(I input);
    I mapToEntity(O output);

    List<O> mapToDtoList(List<I> inputList);
    List<I> mapToEntityList(List<O> outputList);
}