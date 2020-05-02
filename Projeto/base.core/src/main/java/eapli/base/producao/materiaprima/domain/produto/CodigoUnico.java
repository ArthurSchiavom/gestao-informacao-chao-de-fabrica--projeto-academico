package eapli.base.producao.materiaprima.domain.produto;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

import java.util.Objects;

import static java.util.Objects.hash;

@Embeddable
public class CodigoUnico implements ValueObject, Comparable<CodigoUnico> {

    private static final long serialVersionUID = 1L;

    public String CODIGO_UNICO;

    // Embeddable constructor
    protected CodigoUnico() {
    }

    protected CodigoUnico(String codigoUnico) {
        this.CODIGO_UNICO = codigoUnico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodigoUnico)) {
            return false;
        }

        final CodigoUnico that = (CodigoUnico) o;

        return this.CODIGO_UNICO.equals(that.CODIGO_UNICO);
    }

    @Override
    public int hashCode() {
        return CODIGO_UNICO.hashCode();
    }

    @Override
    public String toString() {
        return CODIGO_UNICO;
    }

    @Override
    public int compareTo(CodigoUnico obj) {
        return this.CODIGO_UNICO.compareTo(obj.CODIGO_UNICO);
    }
}

