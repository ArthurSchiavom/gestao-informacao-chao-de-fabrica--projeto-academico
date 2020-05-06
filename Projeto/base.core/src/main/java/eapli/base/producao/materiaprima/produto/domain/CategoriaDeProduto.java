package eapli.base.producao.materiaprima.produto.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CategoriaDeProduto implements ValueObject, Serializable, Comparable<CategoriaDeProduto> {

    private static final long serialVersionUID = 1L;

    private final String categoria;

    protected CategoriaDeProduto() {
        categoria = null;
    }

    protected CategoriaDeProduto(String categoria) {
        this.categoria = categoria;
    }

    public static CategoriaDeProduto valueOf(String categoria) {
        return new CategoriaDeProduto(categoria);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaDeProduto)) {
            return false;
        }

        final CategoriaDeProduto that = (CategoriaDeProduto) o;

        return this.categoria.equalsIgnoreCase(that.categoria);
    }

    @Override
    public int hashCode() {
        return categoria.hashCode();
    }

    @Override
    public String toString() {
        return categoria;
    }

    @Override
    public int compareTo(CategoriaDeProduto obj) {
        return categoria.toLowerCase().compareTo(obj.categoria.toLowerCase());
    }
}

