package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CodigoComercial implements ValueObject, Serializable, Comparable<CodigoComercial> {

    private static final long serialVersionUID = 1L;

    public final String codigoComercialValor;

    protected CodigoComercial() {
        codigoComercialValor = null;
    }

    public static CodigoComercial valueOf(String codigoComercial) throws IllegalDomainValueException {
        return new CodigoComercial(codigoComercial);
    }

    public CodigoComercial(String codigoComercial) throws IllegalDomainValueException {
        if (codigoComercial == null || codigoComercial.isEmpty())
            throw new IllegalArgumentException("O código único deve existir e não ser vazio");
        RepositoryFactory repositoryFactory = PersistenceContext.repositories();
        ProdutoRepository produtoRepository = repositoryFactory.produto();
        if (produtoRepository.produtoDeCodigoComercial(codigoComercial).isPresent()) {
            throw new IllegalDomainValueException("O código comercial indicado já está registado", IllegalDomainValueType.ALREADY_EXISTS);
        }
        this.codigoComercialValor = codigoComercial;
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

        return this.codigoComercialValor.equals(that.codigoComercialValor);
    }

    @Override
    public int hashCode() {
        return codigoComercialValor.hashCode();
    }

    @Override
    public String toString() {
        return codigoComercialValor;
    }

    @Override
    public int compareTo(CodigoComercial obj) {
        return this.codigoComercialValor.compareTo(obj.codigoComercialValor);
    }
}
