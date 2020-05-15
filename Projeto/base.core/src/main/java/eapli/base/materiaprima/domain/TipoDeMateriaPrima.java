package eapli.base.materiaprima.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum TipoDeMateriaPrima {
    MATERIAL("Material"), PRODUTO("Produto");

    public final String name;

    TipoDeMateriaPrima(String name) {
        this.name = name;
    }

}
