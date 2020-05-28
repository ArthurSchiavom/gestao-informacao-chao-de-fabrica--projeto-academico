package eapli.base.gestaoproducao.gestaoerrosnotificacao.domain;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.NotificacaoErroDTO;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.infrastructure.application.HasDTO;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;

@Entity
public class NotificacaoErro implements AggregateRoot<Long>, HasDTO<NotificacaoErroDTO> {
	@Version
	private Long version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public final Long id;

	public final IdentificadorLinhaProducao idLinhaProd;

	public final Long idMensagem;

	public final TipoErroNotificacao tipoErroNotificacao;

	public final EstadoErroNotificacao estadoErro;

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
	                       Long idMensagem, LinhaProducaoRepository linhaProdRepo,
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
		this.estadoErro = EstadoErroNotificacao.ATIVO;
	}

	protected NotificacaoErro() {
		//FOR ORM
		this.id = null;
		this.idLinhaProd = null;
		this.idMensagem = null;
		this.tipoErroNotificacao = null;
		this.estadoErro = null;
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
	public Long identity() {
		return this.id;
	}

	@Override
	public NotificacaoErroDTO toDTO() {
		return new NotificacaoErroDTO(id, idLinhaProd.toString(), idMensagem, tipoErroNotificacao);
	}
}
