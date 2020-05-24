package eapli.base.gestaoproducao.gestaomaterial.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Entity
public class Categoria implements AggregateRoot<CodigoAlfanumericoCategoria> {

    @Version
    private Long version;

    @EmbeddedId
    @XmlAttribute(name = "codigoAlfanumerico")
    public final CodigoAlfanumericoCategoria codigoAlfanumericoCategoria;
    @XmlElement
    private String descricao; // might change so it's not final

    protected Categoria() {
        this.codigoAlfanumericoCategoria = null;
    }

    public Categoria(CodigoAlfanumericoCategoria codigoAlfanumericoCategoria, String descricao) {
        this.codigoAlfanumericoCategoria = codigoAlfanumericoCategoria;
        this.descricao = descricao;
    }


    public static String identityAttributeName(){
            return"codigoAlfanumericoCategoria";
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
    public CodigoAlfanumericoCategoria identity(){
        return this.codigoAlfanumericoCategoria;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                ", codigoAlfanumericoCategoria=" + codigoAlfanumericoCategoria.obterCodigoAlfanumerico() +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}