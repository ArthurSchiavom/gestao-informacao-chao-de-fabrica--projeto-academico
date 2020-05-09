package eapli.base.definircategoriamaterial.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CodigoAlfanumerico implements ValueObject, Comparable<CodigoAlfanumerico> {

    private static final long serialVersionUID = 1L;

    private String codigoAlfaNumerico;

    protected CodigoAlfanumerico() {
    }

    /**
     *
     * @param codigoAlfanumerico "Uma categoria tem um código (alfanumérico 10 caracteres no máximo) e uma descrição.
     * A semântica é dada pelo utilizador do sistema."
     *
     * @throws IllegalArgumentException caso o codigo alfanumerico seja invalido
     */
    public CodigoAlfanumerico(String codigoAlfanumerico) throws IllegalArgumentException{
        if(codigoAlfanumerico == null || codigoAlfanumerico.length()>10 || codigoAlfanumerico.trim().length() == 0) {
            throw new IllegalArgumentException("Código alfanumero não válido");
        }
        this.codigoAlfaNumerico = codigoAlfanumerico;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodigoAlfanumerico that = (CodigoAlfanumerico) o;
        return codigoAlfaNumerico.equals(that.codigoAlfaNumerico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoAlfaNumerico);
    }

    @Override
    public String toString() {
        return "CodigoAlfaNumerico-->" + codigoAlfaNumerico ;
    }

    @Override
    public int compareTo(CodigoAlfanumerico obj) {
        return this.codigoAlfaNumerico.compareTo(obj.codigoAlfaNumerico);
    }
}