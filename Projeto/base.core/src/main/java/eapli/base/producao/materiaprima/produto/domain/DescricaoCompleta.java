package eapli.base.producao.materiaprima.produto.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DescricaoCompleta implements ValueObject, Serializable, Comparable<DescricaoCompleta> {

    private static final long serialVersionUID = 1L;

    private final String descricaoCompleta;

    protected DescricaoCompleta() {
        descricaoCompleta = null;
    }

    protected DescricaoCompleta(String descricaoCompleta) {
        if (descricaoCompleta == null || descricaoCompleta.isEmpty()) {
            throw new IllegalArgumentException("A descrição completa não pode ser vazia.");
        }
        this.descricaoCompleta = descricaoCompleta;
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

        return this.descricaoCompleta.equals(that.descricaoCompleta);
    }

    @Override
    public int hashCode() {
        return descricaoCompleta.hashCode();
    }

    @Override
    public String toString() {
        return descricaoCompleta;
    }

    @Override
    public int compareTo(DescricaoCompleta obj) {
        return this.descricaoCompleta.compareTo(obj.descricaoCompleta);
    }
}

