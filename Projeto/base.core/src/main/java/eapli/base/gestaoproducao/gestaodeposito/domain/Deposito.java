package eapli.base.gestaoproducao.gestaodeposito.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class Deposito implements AggregateRoot<CodigoDeposito> {

	@Version
	private Long version;

	public Deposito() {
		//For ORM
		this.codigo = null;
		this.descricao = null;
	}

	public static String identityAttributeName() {
		return "codigo";
	}

	@EmbeddedId
	private final CodigoDeposito codigo;
	private final String descricao;

	public Deposito(String codigo, String descricao) {
		this.codigo = new CodigoDeposito(codigo);
		this.descricao = descricao;
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
	public CodigoDeposito identity() {
		return this.codigo;
	}
}