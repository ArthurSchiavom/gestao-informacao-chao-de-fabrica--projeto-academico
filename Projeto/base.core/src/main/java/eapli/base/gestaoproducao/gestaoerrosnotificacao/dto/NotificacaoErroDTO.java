package eapli.base.gestaoproducao.gestaoerrosnotificacao.dto;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;

public class NotificacaoErroDTO {
	public final Long identifier;
	public final String idLinhaProd;
	public final Long idMensagem;
	public final TipoErroNotificacao tipoErroNotificacao;

	public NotificacaoErroDTO(Long identifier, String idLinhaProd, Long idMensagem, TipoErroNotificacao tipoErroNotificacao) {
		this.identifier = identifier;
		this.idLinhaProd = idLinhaProd;
		this.idMensagem = idMensagem;
		this.tipoErroNotificacao = tipoErroNotificacao;
	}

	@Override
	public String toString() {
		return "Notificação Erro " + identifier + ": \n" +
				"  Linha de Produção: " + idLinhaProd + "\n" +
				"  ID de Mensagem: " + idMensagem + "\n" +
				"  Tipo de Erro: " + tipoErroNotificacao;
	}
}
