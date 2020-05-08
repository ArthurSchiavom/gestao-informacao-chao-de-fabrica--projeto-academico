package eapli.base.producao.materiaprima.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UnidadeDeMedida implements ValueObject, Serializable, Comparable<UnidadeDeMedida> {

    private static final long serialVersionUID = 1L;

    public final String unidadeDeMedidaValor;

    public UnidadeDeMedida() {
        unidadeDeMedidaValor = null;
    }

    public UnidadeDeMedida(String unidadeDeMedida) {
        this.unidadeDeMedidaValor = unidadeDeMedida;
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

        return this.unidadeDeMedidaValor.equalsIgnoreCase(that.unidadeDeMedidaValor);
    }

    @Override
    public int hashCode() {
        return unidadeDeMedidaValor.hashCode();
    }

    @Override
    public String toString() {
        return unidadeDeMedidaValor;
    }

    @Override
    public int compareTo(UnidadeDeMedida obj) {
        return unidadeDeMedidaValor.toLowerCase().compareTo(obj.unidadeDeMedidaValor.toLowerCase());
    }
}

