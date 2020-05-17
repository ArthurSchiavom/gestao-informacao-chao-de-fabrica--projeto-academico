package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
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

    protected DescricaoCompleta(String descricaoCompleta) throws IllegalDomainValueException {
        if (descricaoCompleta == null) {
            throw new IllegalArgumentException("A descrição completa não pode ser vazia.");
        }
        if (descricaoCompleta.isEmpty()) {
            throw new IllegalDomainValueException("A descrição breve deve ser especificada", IllegalDomainValueType.ILLEGAL_VALUE);
        }
        this.descricaoCompletaValor = descricaoCompleta;
    }

    public static DescricaoCompleta valueOf(String descricaoCompleta) throws IllegalDomainValueException {
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

