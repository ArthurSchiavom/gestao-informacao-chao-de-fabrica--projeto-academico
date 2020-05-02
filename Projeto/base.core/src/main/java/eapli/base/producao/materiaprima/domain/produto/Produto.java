package eapli.base.producao.materiaprima.domain.produto;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class Produto implements AggregateRoot<CodigoUnico> {

    @Version
    private Long version;

    @EmbeddedId
    private CodigoUnico codigoUnico;
    public static String identityAttributeName() {
        return "codigoUnico";
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public CodigoUnico identity() {
        return this.codigoUnico;
    }
}
