package eapli.base.infrastructure.application;

import java.util.ArrayList;
import java.util.List;

public class DTOUtils {
    public static <DTO_TYPE> List<DTO_TYPE> toDTOList(List<HasDTO<DTO_TYPE>> objects) {
        List<DTO_TYPE> resultado = new ArrayList<>();
        for (HasDTO<DTO_TYPE> obj : objects) {
            resultado.add(obj.toDTO());
        }
        return resultado;
    }
}
