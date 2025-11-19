// src/main/java/py/edu/uc/lp32025/mappers/AbstractBaseMapper.java
package py.edu.uc.lp32025.mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractBaseMapper<I, O> implements BaseMapper<I, O> {

    @Override
    public List<O> mapToDtoList(List<I> inputList) {
        if (inputList == null || inputList.isEmpty()) {
            return Collections.emptyList();
        }
        return inputList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<I> mapToEntityList(List<O> outputList) {
        if (outputList == null || outputList.isEmpty()) {
            return Collections.emptyList();
        }
        return outputList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}