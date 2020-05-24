package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.domain.repositories.TransactionalContext;

import javax.annotation.Nullable;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlValue;
import java.io.Serializable;

@Embeddable
public class CodigoComercial implements ValueObject, Serializable, Comparable<CodigoComercial> {

    private static final long serialVersionUID = 1L;

    @XmlValue
    public final String codigoComercialValor;

    protected CodigoComercial() {
        codigoComercialValor = null;
    }

    public CodigoComercial(String codigoComercial, @Nullable ProdutoRepository repo) throws IllegalDomainValueException {
        if (codigoComercial == null) {
            throw new IllegalArgumentException("O código único deve existir");
        }
        if (codigoComercial.isEmpty()) {
             throw new IllegalDomainValueException("O código comercial deve existir e não ser vazio", IllegalDomainValueType.ILLEGAL_VALUE);
        }

//        if (repo == null) {
//            RepositoryFactory repositoryFactory = PersistenceContext.repositories();
//            repo = repositoryFactory.produto();
//        }
//
//
//        if (repo.produtoDeCodigoComercial(codigoComercial).isPresent()) {
//            throw new IllegalDomainValueException("O código comercial indicado já está registado", IllegalDomainValueType.ALREADY_EXISTS);
//        }
        this.codigoComercialValor = codigoComercial;
    }

    public static CodigoComercial valueOf(String codigoComercial, @Nullable ProdutoRepository repo) throws IllegalDomainValueException {
        return new CodigoComercial(codigoComercial, repo);
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

