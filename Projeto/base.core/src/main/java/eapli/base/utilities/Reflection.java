package eapli.base.utilities;

import java.lang.reflect.Field;

public class Reflection {
    /**
     * Finds the name of the first attribute found of type attributeClass in the class targetClass.
     *
     * @param targetClass class where the attribute will be searched in
     * @param attributeClass class of the attribute being searched for
     * @return (1) The name of the first attribute found of type attributeClass in the class targetClass or (2) null if no such attribute is found
     */
    public static String retrieveAttributeName(Class targetClass, Class attributeClass) {
        for (Field field: targetClass.getDeclaredFields()) {
            if (field.getClass().equals(attributeClass))
                return field.getName();
        }
        return null;
    }
}
