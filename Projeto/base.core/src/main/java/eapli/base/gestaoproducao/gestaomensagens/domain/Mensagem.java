package eapli.base.gestaoproducao.gestaomensagens.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;

/**
 * http://www.thejavageek.com/2014/05/14/jpa-single-table-inheritance-example/
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType=DiscriminatorType.STRING)
public abstract class Mensagem implements AggregateRoot<Long> {

	@Version
	private Long version;

	@Id
	@GeneratedValue
	private Long identifier; // can be public bc its final
	public final TipoDeMensagem tipo;
	public final TimestampEmissao tempoEmissao;


	public Mensagem(TipoDeMensagem tipo, TimestampEmissao tempoEmissao) {
		if((tipo == null || tempoEmissao == null)){
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
	public Long identity() {
		return this.identifier;
	}

	@Override
	public String toString() {
		return "Mensagem{" +
				"identifier=" + identifier +
				", tipo=" + tipo +
				", tempoEmissao=" + tempoEmissao +
				'}';
	}
}