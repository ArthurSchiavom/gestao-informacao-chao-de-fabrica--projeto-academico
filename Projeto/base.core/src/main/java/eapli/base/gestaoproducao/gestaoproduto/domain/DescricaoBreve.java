package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlValue;
import java.io.Serializable;

@Embeddable
public class DescricaoBreve implements ValueObject, Serializable, Comparable<DescricaoBreve> {

    private static final long serialVersionUID = 1L;
    private static final int MAX_CARACTERES_DESCRICAO_BREVE = 30;

    @XmlValue
    public final String descricaoBreveValor;

    protected DescricaoBreve() {
        descricaoBreveValor = null;
    }

    protected DescricaoBreve(String descricaoBreve) throws IllegalDomainValueException {
        if (descricaoBreve == null) {
            throw new IllegalArgumentException("A descricao breve não pode ser null");
        }
        if (descricaoBreve.length() > MAX_CARACTERES_DESCRICAO_BREVE) {
            throw new IllegalDomainValueException("A descrição breve deve ter no máximo " + MAX_CARACTERES_DESCRICAO_BREVE + " caractéres", IllegalDomainValueType.TOO_MANY_CHARACTERS);
        }
        if (descricaoBreve.isEmpty()) {
            throw new IllegalDomainValueException("Deve fornecer uma descrição breve", IllegalDomainValueType.TOO_MANY_CHARACTERS);
        }
        this.descricaoBreveValor = descricaoBreve;
    }

    public static DescricaoBreve valueOf(String descricaoBreve) throws IllegalDomainValueException {
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

