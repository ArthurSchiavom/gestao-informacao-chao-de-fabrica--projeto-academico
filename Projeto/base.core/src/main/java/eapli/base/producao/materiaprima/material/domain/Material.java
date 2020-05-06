package eapli.base.producao.materiaprima.material.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class Material implements AggregateRoot<CodigoInterno> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    public static String identityAttributeName() {
        return "codigoInterno";
    }

    @EmbeddedId
    private CodigoInterno codigoInterno;


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
    public CodigoInterno identity() {
        return this.codigoInterno;
    }
}
