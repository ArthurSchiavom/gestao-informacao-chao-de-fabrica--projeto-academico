package eapli.base.gestaoproducao.ordemProducao.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlValue;
import java.util.Objects;

/**
 * Identificador da ordem de produção
 */
@Embeddable
public class IdentificadorOrdemProducao implements ValueObject, Comparable<IdentificadorOrdemProducao> {

    private static final long serialVersionUID = 1L;

    @XmlValue
    public final String identificador;

    public IdentificadorOrdemProducao() {
        identificador = "";
    }

    public IdentificadorOrdemProducao(String identificador) {
        if (identificador == null || identificador.isEmpty()) {
            throw new IllegalArgumentException("O identificador de ordem deve existir.");
        }
        this.identificador = identificador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentificadorOrdemProducao that = (IdentificadorOrdemProducao) o;
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
    public int compareTo(IdentificadorOrdemProducao obj) {
        return this.identificador.compareTo(obj.identificador);
    }
}