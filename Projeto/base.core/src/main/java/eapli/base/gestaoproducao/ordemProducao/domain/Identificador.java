package eapli.base.gestaoproducao.ordemProducao.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Identificador da ordem de produção
 */
@Embeddable
public class Identificador implements ValueObject, Comparable<Identificador> {

    private static final long serialVersionUID = 1L;

    public final String identificador;

    public Identificador() {
        identificador = "";
    }

    public Identificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identificador that = (Identificador) o;
        return identificador.equals(that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return "Identificador{" +
                "identificador='" + identificador + '\'' +
                '}';
    }

    @Override
    public int compareTo(Identificador obj) {
        return this.identificador.compareTo(obj.identificador);
    }
}