package eapli.base.producao.materiaprima.produto.domain;

import eapli.base.infrastructure.domain.IllegalDomainValue;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DescricaoBreve implements ValueObject, Serializable, Comparable<DescricaoBreve> {

    private static final long serialVersionUID = 1L;
    private static final int MAX_CARACTERES_DESCRICAO_BREVE = 30;

    public final String descricaoBreveValor;

    protected DescricaoBreve() {
        descricaoBreveValor = null;
    }

    protected DescricaoBreve(String descricaoBreve) throws IllegalDomainValue {
        if (descricaoBreve == null) {
            throw new IllegalArgumentException("A descricao breve não pode ser null");
        }
        if (descricaoBreve.length() > MAX_CARACTERES_DESCRICAO_BREVE) {
            throw new IllegalDomainValue("A descrição breve deve ter no máximo " + MAX_CARACTERES_DESCRICAO_BREVE + " caractéres", IllegalDomainValueType.TOO_MANY_CHARACTERS);
        }
        this.descricaoBreveValor = descricaoBreve;
    }

    public static DescricaoBreve valueOf(String descricaoBreve) throws IllegalDomainValue {
        return new DescricaoBreve(descricaoBreve);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DescricaoBreve)) {
            return false;
        }

        final DescricaoBreve that = (DescricaoBreve) o;

        return this.descricaoBreveValor.equals(that.descricaoBreveValor);
    }

    @Override
    public int hashCode() {
        return descricaoBreveValor.hashCode();
    }

    @Override
    public String toString() {
        return descricaoBreveValor;
    }

    @Override
    public int compareTo(DescricaoBreve obj) {
        return this.descricaoBreveValor.compareTo(obj.descricaoBreveValor);
    }
}

