package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.util.Objects;

/**
 * http://www.thejavageek.com/2014/05/14/jpa-single-table-inheritance-example/
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Mensagem implements AggregateRoot<Long> {

    @Version
    private Long version;

    @Id
    @GeneratedValue
    private Long identifier; // can be public bc its final
    @Column(insertable=false, updatable=false)
    public final TipoDeMensagem tipo;
    public final TimestampEmissao tempoEmissao;


    public Mensagem(TipoDeMensagem tipo, TimestampEmissao tempoEmissao) {
        if ((tipo == null || tempoEmissao == null)) {
            throw new IllegalArgumentException("Mensagem não pode ter parametros null");
        }
        this.tipo = tipo;
        this.tempoEmissao = tempoEmissao;
    }

    protected Mensagem() {
        //FOR ORM
        this.identifier = null;
        this.tipo = null;
        this.tempoEmissao = null;
    }

    public static String identityAttributeName() {
        return "identifier";
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensagem mensagem = (Mensagem) o;
        return tipo == mensagem.tipo &&
                tempoEmissao.equals(mensagem.tempoEmissao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, tempoEmissao);
    }

    @Override
    public Long identity() {
        return identifier;
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "tipo=" + tipo +
                ", tempoEmissao=" + tempoEmissao +
                '}';
    }
}
