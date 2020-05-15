package eapli.base.definircategoriamaterial.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Descricao implements ValueObject, Serializable, Comparable<Descricao> {

    private static final long serialVersionUID = 1L;

    public final String descricaoValor;

    protected Descricao() {
        descricaoValor = null;
    }

    protected Descricao(String descricao) {
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode ser vazia.");
        }
        this.descricaoValor = descricao;
    }

    public static Descricao valueOf(String descricaoCompleta) {
        return new Descricao(descricaoCompleta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Descricao)) {
            return false;
        }

        final Descricao that = (Descricao) o;

        return this.descricaoValor.equals(that.descricaoValor);
    }

    @Override
    public int hashCode() {
        return descricaoValor.hashCode();
    }

    @Override
    public String toString() {
        return descricaoValor;
    }

    @Override
    public int compareTo(Descricao obj) {
        return this.descricaoValor.compareTo(obj.descricaoValor);
    }
}

