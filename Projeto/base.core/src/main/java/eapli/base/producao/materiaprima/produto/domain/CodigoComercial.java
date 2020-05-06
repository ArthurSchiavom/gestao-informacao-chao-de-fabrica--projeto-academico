package eapli.base.producao.materiaprima.produto.domain;

import eapli.base.infrastructure.domain.IllegalDomainValue;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.producao.materiaprima.produto.persistence.ProdutoRepository;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CodigoComercial implements ValueObject, Serializable, Comparable<CodigoComercial> {

    private static final long serialVersionUID = 1L;

    protected final String codigoComercial;

    protected CodigoComercial() {
        codigoComercial = null;
    }

    public static CodigoComercial valueOf(String codigoComercial) throws IllegalDomainValue {
        return new CodigoComercial(codigoComercial);
    }

    public CodigoComercial(String codigoComercial) throws IllegalDomainValue {
        if (codigoComercial == null || codigoComercial.isEmpty())
            throw new IllegalArgumentException("O código único deve existir e não ser vazio");
        RepositoryFactory repositoryFactory = PersistenceContext.repositories();
        ProdutoRepository produtoRepository = repositoryFactory.produto();
        if (produtoRepository.produtoOfCodigoComercial(codigoComercial).isPresent()) {
            throw new IllegalDomainValue("O código comercial indicado já está registado", IllegalDomainValueType.ALREADY_EXISTS);
        }
        this.codigoComercial = codigoComercial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodigoComercial)) {
            return false;
        }

        final CodigoComercial that = (CodigoComercial) o;

        return this.codigoComercial.equals(that.codigoComercial);
    }

    @Override
    public int hashCode() {
        return codigoComercial.hashCode();
    }

    @Override
    public String toString() {
        return codigoComercial;
    }

    @Override
    public int compareTo(CodigoComercial obj) {
        return this.codigoComercial.compareTo(obj.codigoComercial);
    }
}

