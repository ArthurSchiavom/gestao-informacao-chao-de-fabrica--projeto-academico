package eapli.base.gestaoproducao.ordemProducao.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

@Embeddable
public class IdentificadorEncomenda implements ValueObject, Comparable<IdentificadorEncomenda> {

    private static final long serialVersionUID = 1L;

    @XmlValue
    public final String identificador;

    protected IdentificadorEncomenda() {
        identificador = "";
    }

    public IdentificadorEncomenda(String identificador) {
        if (identificador == null) {
            throw new IllegalArgumentException("O identificador de encomenda deve existir.");
        }
        identificador = identificador.trim();
        if (identificador.isEmpty()) {
            throw new IllegalArgumentException("O identificador de encomenda deve existir.");
        }

        this.identificador = identificador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentificadorEncomenda that = (IdentificadorEncomenda) o;
        return Objects.equals(identificador, that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return "IdentificadorEncomenda{" +
                "identificador='" + identificador + '\'' +
                '}';
    }

    @Override
    public int compareTo(IdentificadorEncomenda obj) {
        return this.identificador.compareTo(obj.identificador);
    }
}