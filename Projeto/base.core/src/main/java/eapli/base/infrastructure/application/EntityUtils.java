package eapli.base.infrastructure.application;

import eapli.framework.domain.model.AggregateRoot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityUtils {
    public static
            <ID_CLASS extends Comparable<ID_CLASS>,
            ENTITY_CLASS extends AggregateRoot<ID_CLASS>>
                Map<ID_CLASS, ENTITY_CLASS> mapEntityToId(Collection<ENTITY_CLASS> entities) {

        Map<ID_CLASS, ENTITY_CLASS> entityClassMap = new HashMap<>();
        for (ENTITY_CLASS entity : entities) {
            entityClassMap.put(entity.identity(), entity);
        }
        return entityClassMap;
    }

    public static
            <ID_CLASS extends Comparable<ID_CLASS>,
            ENTITY_CLASS extends AggregateRoot<ID_CLASS>>
                Map<String, ENTITY_CLASS> mapIdStringToEntity(Collection<ENTITY_CLASS> entities) {

        Map<String, ENTITY_CLASS> entityClassMap = new HashMap<>();
        for (ENTITY_CLASS entity : entities) {
            entityClassMap.put(entity.identity().toString(), entity);
        }
        return entityClassMap;
    }
}
