package eapli.base.producao.materiaprima.material.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CodigoInterno implements ValueObject, Serializable, Comparable<CodigoInterno> {

    private static final long serialVersionUID = 1L;

    private final String codigoInterno;

    public CodigoInterno() {
        codigoInterno = null;
    }

    public CodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodigoInterno)) {
            return false;
        }

        final CodigoInterno that = (CodigoInterno) o;

        return codigoInterno.equals(that.codigoInterno);
    }

    @Override
    public int hashCode() {
        return codigoInterno.hashCode();
    }

    @Override
    public String toString() {
        return codigoInterno;
    }

    @Override
    public int compareTo(CodigoInterno obj) {
        return codigoInterno.compareTo(obj.codigoInterno);
    }
}

