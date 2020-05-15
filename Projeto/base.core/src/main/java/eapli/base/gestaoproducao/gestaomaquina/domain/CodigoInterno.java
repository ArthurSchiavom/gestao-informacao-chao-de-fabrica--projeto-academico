package eapli.base.gestaoproducao.gestaomaquina.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CodigoInterno implements ValueObject, Comparable<CodigoInterno> {

    private static final long serialVersionUID = 1L;

    private String codigoInterno;


    protected CodigoInterno(){
    }

    public CodigoInterno(String codigoInterno) throws IllegalArgumentException{
        if(codigoInterno == null || codigoInterno.trim().isEmpty()) {
            throw new IllegalArgumentException("Código interno não válido");
        }
        this.codigoInterno = codigoInterno;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodigoInterno that = (CodigoInterno) o;
        return codigoInterno.equals(that.codigoInterno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoInterno);
    }

    @Override
    public String toString() {
        return "CodigoInterno{" +
                "codigoInterno='" + codigoInterno + '\'' +
                '}';
    }

    @Override
    public int compareTo(CodigoInterno obj) {
        return this.codigoInterno.compareTo(obj.codigoInterno);
    }
}