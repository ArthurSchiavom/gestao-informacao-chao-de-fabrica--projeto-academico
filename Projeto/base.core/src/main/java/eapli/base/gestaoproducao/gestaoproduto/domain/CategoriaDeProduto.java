package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
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

    protected CategoriaDeProduto(String categoria) throws IllegalDomainValueException {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria não pode ser null ou vazia");
        }
        if (categoria.isEmpty()) {
            throw new IllegalDomainValueException("A categoria não pode ser null ou vazia", IllegalDomainValueType.ILLEGAL_VALUE);
        }
        this.categoriaValor = categoria;
    }

    public static CategoriaDeProduto valueOf(String categoria) throws IllegalDomainValueException {
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

