package eapli.base.gestaoproducao.gestaoerrosnotificacao.domain;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.NotificacaoErroDTO;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemID;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.infrastructure.application.ConvertableToDTO;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Entity
public class NotificacaoErro implements AggregateRoot<Long>, ConvertableToDTO<NotificacaoErroDTO> {
	@Version
	private Long version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlAttribute(name = "idNotificacao")
	public final Long id;

	@XmlElement(name = "linhaDeProducao")
	public final IdentificadorLinhaProducao idLinhaProd;

	@XmlElement(name = "mensagem")
	public final MensagemID idMensagem;

	@XmlElement(name = "tipo")
	@Enumerated(EnumType.STRING)
	public final TipoErroNotificacao tipoErroNotificacao;

	@XmlElement(name = "estado")
	@Enumerated(EnumType.STRING)
	private EstadoNotificacaoErro estadoErro;

	/**
	 * Cria uma nova notificação de erro
	 *
	 * @param idLinhaProd   o id da linha de produção cuja qual a notificação de erro pertence
	 * @param tipoErro      o tipo de erro da notificação
	 * @param idMensagem    o id da mensagem que tem um erro
	 * @param linhaProdRepo o repositório das linhas de produção
	 * @param msgRepo       o repositório das mensagens
	 */
	public NotificacaoErro(IdentificadorLinhaProducao idLinhaProd, TipoErroNotificacao tipoErro,
	                       MensagemID idMensagem, LinhaProducaoRepository linhaProdRepo,
	                       MensagemRepository msgRepo) {
		if (idLinhaProd == null || idMensagem == null || tipoErro == null) {
			throw new IllegalArgumentException("Values can't be null");
		}
		if (msgRepo == null || linhaProdRepo == null) {
			throw new IllegalArgumentException("Repositories can't be null");
		}
		if (!linhaProdRepo.containsOfIdentity(idLinhaProd)) {
			throw new IllegalArgumentException("Linha de produção introduzida não existe");
		}
		if (!msgRepo.containsOfIdentity(idMensagem)) {
			throw new IllegalArgumentException("Mensagem introduzida não existe");
		}

		this.id = null;
		this.idLinhaProd = idLinhaProd;
		this.idMensagem = idMensagem;
		this.tipoErroNotificacao = tipoErro;
		this.estadoErro = EstadoNotificacaoErro.ATIVO;
	}

	protected NotificacaoErro() {
		//FOR ORM
		this.id = null;
		this.idLinhaProd = null;
		this.idMensagem = null;
		this.tipoErroNotificacao = null;
		this.estadoErro = null;
	}

	public static NotificacaoErro gerarNotificacaoDeErro(TipoErroNotificacao tipoErroNotificacao, LinhaProducao linhaProducao, LinhaProducaoRepository linhaProducaoRepository, MensagemRepository mensagemRepository, Mensagem mensagem) {
		switch (tipoErroNotificacao) {
			case DADOS_INVALIDOS:
				return new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS, mensagem.identity(), linhaProducaoRepository, mensagemRepository);
			case ELEMENTOS_INEXISTENTES:
				return new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.ELEMENTOS_INEXISTENTES, mensagem.identity(), linhaProducaoRepository, mensagemRepository);
		}
		return null;
	}

	public static String identityAttributeName() {
		return "id";
	}

	boolean arquivar() {
		if(estadoErro == EstadoNotificacaoErro.ARQUIVADO) {
			return false; //Não se pode arquivar se já estiver arquivado
		}
		estadoErro = EstadoNotificacaoErro.ARQUIVADO;
		return true;
	}

	boolean isArquivado() {
		if(estadoErro == EstadoNotificacaoErro.ARQUIVADO) {
			return true;
		}
		return false;
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
		return this.id;
	}

	@Override
	public NotificacaoErroDTO toDTO() {
		return new NotificacaoErroDTO(id, idLinhaProd.toString(), tipoErroNotificacao.nomeDisplay,
				estadoErro.nomeDisplay, idMensagem.tempoEmissao.timestamp,
				idMensagem.tipoDeMensagem.nomeDisplay, idMensagem.codigoInternoMaquina.codigoInterno);
	}
}
