package eapli.base.gestaoproducao.gestaolinhasproducao.domain;

import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;
import eapli.base.infrastructure.application.HasDTO;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;
import javax.xml.bind.annotation.*;

@Entity
public class LinhaProducao implements AggregateRoot<IdentificadorLinhaProducao>, HasDTO<LinhaProducaoDTO> {

	@Version
	private Long version;

	@XmlAttribute(name = "identificador")
	@EmbeddedId
	public final IdentificadorLinhaProducao identifier; // can be public bc its final

	@XmlElement
	private EstadoProcessamentoMensagens estado;

	public LinhaProducao(final String identifier) {
		this.identifier = new IdentificadorLinhaProducao(identifier);
		this.estado = EstadoProcessamentoMensagens.SUSPENSO;
	}

	public LinhaProducao() {
		//FOR ORM
		this.identifier = null;
	}

	public static String identityAttributeName() {
		return "id";
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

	@Override
	public String toString() {
		return "LinhaProducao{ Identificador: " + identifier.identifier+", estado: "+ estado.toString()+"}";
	}

	@Override
	public LinhaProducaoDTO toDTO() {
		return new LinhaProducaoDTO(identity().toString());
	}
}