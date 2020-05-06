package eapli.base.producao.materiaprima.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class MateriaPrima implements AggregateRoot<Codigo> {

    @Version
    private Long version;

    public static String identityAttributeName() {
        return "codigo";
    }

    @EmbeddedId
    private Codigo codigo;


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
    public Codigo identity() {
        return this.codigo;
    }
}
