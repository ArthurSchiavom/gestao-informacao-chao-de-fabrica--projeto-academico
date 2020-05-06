package eapli.base.producao.materiaprima.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UnidadeDeMedida implements ValueObject, Serializable, Comparable<UnidadeDeMedida> {

    private static final long serialVersionUID = 1L;

    private final String unidadeDeMedida;

    public UnidadeDeMedida() {
        unidadeDeMedida = null;
    }

    public UnidadeDeMedida(String unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public static UnidadeDeMedida valueOf(String unidadeDeMedida) {
        return new UnidadeDeMedida(unidadeDeMedida);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnidadeDeMedida)) {
            return false;
        }

        final UnidadeDeMedida that = (UnidadeDeMedida) o;

        return this.unidadeDeMedida.equalsIgnoreCase(that.unidadeDeMedida);
    }

    @Override
    public int hashCode() {
        return unidadeDeMedida.hashCode();
    }

    @Override
    public String toString() {
        return unidadeDeMedida;
    }

    @Override
    public int compareTo(UnidadeDeMedida obj) {
        return unidadeDeMedida.toLowerCase().compareTo(obj.unidadeDeMedida.toLowerCase());
    }
}

