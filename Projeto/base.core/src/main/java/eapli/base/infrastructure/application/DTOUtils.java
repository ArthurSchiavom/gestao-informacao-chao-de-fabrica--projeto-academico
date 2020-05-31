package eapli.base.infrastructure.application;

import eapli.framework.domain.model.AggregateRoot;

import java.util.*;

public class DTOUtils {
    /**
     * Maintains order.
     *
     * @param objects
     * @param <DTO_TYPE>
     * @return
     */
    public static <DTO_TYPE> List<DTO_TYPE> toDTOList(List<? extends ConvertableToDTO<DTO_TYPE>> objects) {
        List<DTO_TYPE> resultado = new ArrayList<>();
        for (ConvertableToDTO<DTO_TYPE> obj : objects) {
            resultado.add(obj.toDTO());
        }
        return resultado;
    }

    /**
     * Maintains order.
     *
     * @param objects
     * @param <DTO_TYPE>
     * @return
     */
    public static <DTO_TYPE> List<DTO_TYPE> toDTOList(ConvertableToDTO<DTO_TYPE>... objects) {
        return toDTOList(Arrays.asList(objects));
    }

    public static
            <DTO_CLASS,
            ID_CLASS extends Comparable<ID_CLASS>,
            ENTITY_CLASS extends AggregateRoot<ID_CLASS> & ConvertableToDTO<DTO_CLASS>>
                Map<ID_CLASS, DTO_CLASS> mapEntityDTOtoId(Collection<ENTITY_CLASS> entities) {

        Map<ID_CLASS, DTO_CLASS> entityClassMap = new HashMap<>();
        for (ENTITY_CLASS entity : entities) {
            entityClassMap.put(entity.identity(), entity.toDTO());
        }
        return entityClassMap;
    }

    public static
            <DTO_CLASS,
            ID_CLASS extends Comparable<ID_CLASS>,
            ENTITY_CLASS extends AggregateRoot<ID_CLASS> & ConvertableToDTO<DTO_CLASS>>
                Map<String, DTO_CLASS> mapIdStringToEntityDTO(Collection<ENTITY_CLASS> entities) {

        Map<String, DTO_CLASS> entityClassMap = new HashMap<>();
        for (ENTITY_CLASS entity : entities) {
            entityClassMap.put(entity.identity().toString(), entity.toDTO());
        }
        return entityClassMap;
    }
}
