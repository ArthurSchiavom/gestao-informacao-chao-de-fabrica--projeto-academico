package eapli.base.gestaolinhasproducao.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class LinhaProducao implements AggregateRoot<IdentificadorLinhaProducao> {

	@Version
	private Long version;

	@EmbeddedId
	private IdentificadorLinhaProducao identifier;
	private EstadoProcessamentoMensagens estado;

	public LinhaProducao(final String identifier) {
		this.identifier = new IdentificadorLinhaProducao(identifier);
		this.estado = EstadoProcessamentoMensagens.INATIVO;
	}

	public LinhaProducao() {
		//FOR ORM
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
	public IdentificadorLinhaProducao identity() {
		return this.identifier;
	}
}