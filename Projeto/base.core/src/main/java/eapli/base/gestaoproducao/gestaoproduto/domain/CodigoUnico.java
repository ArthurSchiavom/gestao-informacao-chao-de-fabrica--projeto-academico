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
public class CodigoUnico implements ValueObject, Serializable, Comparable<CodigoUnico> {

    private static final long serialVersionUID = 1L;

    @XmlValue
    public final String codigoUnicoValor;

    // Embeddable constructor
    protected CodigoUnico() {
        codigoUnicoValor = null;
    }

    public CodigoUnico(String codigoUnico, @Nullable ProdutoRepository repo) throws IllegalDomainValueException {
        if (codigoUnico == null) {
            throw new IllegalArgumentException("O código único deve existir e não ser vazio");
        }
        if (codigoUnico.isEmpty()) {
            throw new IllegalDomainValueException("O código único deve existir e não ser vazio", IllegalDomainValueType.ILLEGAL_VALUE);
        }

        if (repo == null) {
            RepositoryFactory repositoryFactory = PersistenceContext.repositories();
            repo = repositoryFactory.produto();
        }

        if (repo.produtoDeCodigoUnico(codigoUnico).isPresent()) {
            throw new IllegalDomainValueException("O código único indicado já está registado", IllegalDomainValueType.ALREADY_EXISTS);
        }

        this.codigoUnicoValor = codigoUnico;
    }

    public static CodigoUnico valueOf(String codigoUnico, @Nullable ProdutoRepository repo) throws IllegalDomainValueException {
        return new CodigoUnico(codigoUnico, repo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodigoUnico)) {
            return false;
        }

        final CodigoUnico that = (CodigoUnico) o;

        return this.codigoUnicoValor.equals(that.codigoUnicoValor);
    }

    @Override
    public int hashCode() {
        return codigoUnicoValor.hashCode();
    }

    @Override
    public String toString() {
        return codigoUnicoValor;
    }

    @Override
    public int compareTo(CodigoUnico obj) {
        return this.codigoUnicoValor.compareTo(obj.codigoUnicoValor);
    }
}

