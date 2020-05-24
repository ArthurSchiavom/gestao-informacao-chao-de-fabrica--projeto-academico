package eapli.base.gestaoproducao.gestaomaterial.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

@Embeddable
public class CodigoAlfanumericoCategoria implements ValueObject, Comparable<CodigoAlfanumericoCategoria> {

    private static final long serialVersionUID = 1L;

    @XmlValue
    private final String codigoAlfaNumerico;

    protected CodigoAlfanumericoCategoria() {
        codigoAlfaNumerico = null;
    }

    /**
     *
     * @param codigoAlfanumerico "Uma categoria tem um código (alfanumérico 10 caracteres no máximo) e uma descrição.
     * A semântica é dada pelo utilizador do sistema."
     *
     * @throws IllegalArgumentException caso o codigo alfanumerico seja invalido
     */
    public CodigoAlfanumericoCategoria(String codigoAlfanumerico) throws IllegalArgumentException{
        if(codigoAlfanumerico == null || codigoAlfanumerico.length()>10 || codigoAlfanumerico.trim().length() == 0) {
            throw new IllegalArgumentException("Código alfanumero não válido");
        }
        this.codigoAlfaNumerico = codigoAlfanumerico;
    }

    public String obterCodigoAlfanumerico(){
        return codigoAlfaNumerico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodigoAlfanumericoCategoria that = (CodigoAlfanumericoCategoria) o;
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
    public int compareTo(CodigoAlfanumericoCategoria obj) {
        return this.codigoAlfaNumerico.compareTo(obj.codigoAlfaNumerico);
    }
}