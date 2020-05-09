package eapli.base.definircategoriamaterial.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CodigoInterno implements ValueObject, Comparable<CodigoInterno> {

    private static final long serialVersionUID = 1L;

    private String CODIGO_INTERNO;

    protected CodigoInterno() {
    }

    public CodigoInterno(String codigoInterno) {
        this.CODIGO_INTERNO = codigoInterno;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodigoInterno that = (CodigoInterno) o;
        return CODIGO_INTERNO.equals(that.CODIGO_INTERNO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CODIGO_INTERNO);
    }

    @Override
    public String toString() {
        return "CodigoInterno{" +
                "CODIGO_INTERNO='" + CODIGO_INTERNO + '\'' +
                '}';
    }

    @Override
    public int compareTo(CodigoInterno obj) {
        return this.CODIGO_INTERNO.compareTo(obj.CODIGO_INTERNO);
    }
}