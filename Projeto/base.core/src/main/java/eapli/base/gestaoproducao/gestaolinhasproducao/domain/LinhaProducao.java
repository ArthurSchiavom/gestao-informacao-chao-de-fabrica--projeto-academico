package eapli.base.gestaoproducao.gestaolinhasproducao.domain;

import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;
import eapli.base.infrastructure.application.ConvertableToDTO;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class LinhaProducao
		implements AggregateRoot<IdentificadorLinhaProducao>, ConvertableToDTO<LinhaProducaoDTO> {

	@Version
	private Long version;

	@XmlAttribute(name = "identificador")
	@EmbeddedId
	public final IdentificadorLinhaProducao identifier; // can be public bc its final

	@XmlElement
	@Enumerated(EnumType.STRING)
	private EstadoProcessamentoMensagens estado;

	@XmlTransient
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimaVezEstadoAlterado;

	public LinhaProducao(final String identifier) {
		this.identifier = new IdentificadorLinhaProducao(identifier);
		this.estado = EstadoProcessamentoMensagens.SUSPENSO;
		this.ultimaVezEstadoAlterado = new Date();
	}

	public LinhaProducao() {
		//FOR ORM
		this.identifier = null;
		this.ultimaVezEstadoAlterado = null;
	}

	public String estadoProcessamentoMensagens() {
		switch (this.estado) {
			case ATIVO:
				return "ATIVO";
			case SUSPENSO:
				return "SUSPENSO";
		}
		return null;
	}

	/**
	 * Altera o estado da linha de produção para o estado pretendido
	 * @param estadoPretendido o novo estado
	 * @return o sucesso da operação
	 */
	public boolean alterarEstado(EstadoProcessamentoMensagens estadoPretendido) {
		if(!podeMudarParaEstado(estadoPretendido)) {
			return false;
		}
		switch(estadoPretendido) {
			case ATIVO:
				this.estado = EstadoProcessamentoMensagens.ATIVO;
				this.ultimaVezEstadoAlterado = new Date();
				return true;
			case SUSPENSO:
				this.estado = EstadoProcessamentoMensagens.SUSPENSO;
				this.ultimaVezEstadoAlterado = new Date();
				return true;
			default:
				throw new IllegalArgumentException("Estado introduzido é inválido");
		}
	}

	public static String identityAttributeName() {
		return "identifier";
	}

	/**
	 * Converte a data que o objeto tem guardado para uma string legivel para um utilizador
	 * @return uma string legivel
	 */
	public String obterUltimaVezAtualizado() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return dateFormat.format(ultimaVezEstadoAlterado);
	}

	/**
	 * Verifica se o estado para o qual estamos a tentar mudar é válido
	 *
	 * @param estado o estado para o qual estamos a tentar mudar
	 * @return verdadeiro se podermos, falso se não podermos
	 */
	public boolean podeMudarParaEstado(EstadoProcessamentoMensagens estado) {
		return this.estado != estado;
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
		return "LinhaProducao{ Identificador: " + identifier.identifier + ", estado: " + estado.toString() + "}";
	}

	@Override
	public LinhaProducaoDTO toDTO() {
		return new LinhaProducaoDTO(identity().toString(), estadoProcessamentoMensagens(), obterUltimaVezAtualizado());
	}
}