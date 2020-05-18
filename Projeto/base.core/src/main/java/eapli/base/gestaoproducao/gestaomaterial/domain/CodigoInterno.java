package eapli.base.gestaoproducao.gestaomaterial.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CodigoInterno implements ValueObject, Comparable<CodigoInterno> {

    private static final long serialVersionUID = 1L;

    public final String codigoInternoValor;

    protected CodigoInterno() {
        codigoInternoValor = null;
    }

    // TODO - mudar para protected e utilizar valueOf()
    public CodigoInterno(String codigoInterno) {
        if(codigoInterno == null ||  codigoInterno.trim().length() == 0 || codigoInterno==null|| codigoInterno.trim().length()==0 ) {
            throw new IllegalArgumentException("Ficha tecnica pdf não válida");
        }
        this.codigoInternoValor = codigoInterno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodigoInterno that = (CodigoInterno) o;
        return codigoInternoValor.equals(that.codigoInternoValor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoInternoValor);
    }

    @Override
    public String toString() {
        return codigoInternoValor;
    }

    @Override
    public int compareTo(CodigoInterno obj) {
        return this.codigoInternoValor.compareTo(obj.codigoInternoValor);
    }
}