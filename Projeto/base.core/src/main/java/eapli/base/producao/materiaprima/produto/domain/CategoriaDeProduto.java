package eapli.base.producao.materiaprima.produto.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CategoriaDeProduto implements ValueObject, Serializable, Comparable<CategoriaDeProduto> {

    private static final long serialVersionUID = 1L;

    public final String categoriaValor;

    protected CategoriaDeProduto() {
        categoriaValor = null;
    }

    protected CategoriaDeProduto(String categoria) {
        this.categoriaValor = categoria;
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

        return this.categoriaValor.equalsIgnoreCase(that.categoriaValor);
    }

    @Override
    public int hashCode() {
        return categoriaValor.hashCode();
    }

    @Override
    public String toString() {
        return categoriaValor;
    }

    @Override
    public int compareTo(CategoriaDeProduto obj) {
        return categoriaValor.toLowerCase().compareTo(obj.categoriaValor.toLowerCase());
    }
}

