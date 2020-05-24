package eapli.base.gestaoproducao.gestaomateriaprima.domain;

import javax.xml.bind.annotation.XmlValue;

public enum TipoDeMateriaPrima {
    MATERIAL("Material"), PRODUTO("Produto");

    @XmlValue
    public final String name;

    TipoDeMateriaPrima(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
