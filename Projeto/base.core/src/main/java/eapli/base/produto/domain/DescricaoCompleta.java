package eapli.base.materiaprima.produto.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DescricaoCompleta implements ValueObject, Serializable, Comparable<DescricaoCompleta> {

    private static final long serialVersionUID = 1L;

    public final String descricaoCompletaValor;

    protected DescricaoCompleta() {
        descricaoCompletaValor = null;
    }

    protected DescricaoCompleta(String descricaoCompleta) {
        if (descricaoCompleta == null || descricaoCompleta.isEmpty()) {
            throw new IllegalArgumentException("A descrição completa não pode ser vazia.");
        }
        this.descricaoCompletaValor = descricaoCompleta;
    }

    public static DescricaoCompleta valueOf(String descricaoCompleta) {
        return new DescricaoCompleta(descricaoCompleta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DescricaoCompleta)) {
            return false;
        }

        final DescricaoCompleta that = (DescricaoCompleta) o;

        return this.descricaoCompletaValor.equals(that.descricaoCompletaValor);
    }

    @Override
    public int hashCode() {
        return descricaoCompletaValor.hashCode();
    }

    @Override
    public String toString() {
        return descricaoCompletaValor;
    }

    @Override
    public int compareTo(DescricaoCompleta obj) {
        return this.descricaoCompletaValor.compareTo(obj.descricaoCompletaValor);
    }
}

