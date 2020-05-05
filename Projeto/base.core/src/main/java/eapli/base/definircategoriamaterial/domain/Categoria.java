package eapli.base.definircategoriamaterial.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class Categoria implements AggregateRoot<CodigoAlfanumerico> {

    @Version
    private Long version;

    @EmbeddedId
    private CodigoAlfanumerico codigoAlfanumerico;
    private String descricao;

    protected Categoria() {
    }

    public Categoria(CodigoAlfanumerico codigoAlfanumerico, String descricao) {
        this.codigoAlfanumerico = codigoAlfanumerico;
        this.descricao = descricao;
    }


    public static String identityAttributeName(){
            return"codigoAlfanumerico";
    }


    @Override
    public boolean equals(final Object o){
            return DomainEntities.areEqual(this,o);
    }

    @Override
    public int hashCode(){
            return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public CodigoAlfanumerico identity(){
        return this.codigoAlfanumerico;
    }
}