package eapli.base.gestaoproducao.gestaoerrosnotificacao.dto;

import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemID;

public class NotificacaoErroDTO {
	public final Long identifier;
	public final String idLinhaProd;
	public final MensagemID idMensagem;
	public final String tipoErroNotificacao;
	public final String estadoErro;

	public NotificacaoErroDTO(Long identifier, String idLinhaProd, MensagemID idMensagem, String tipoErroNotificacao, String estadoErro) {
		this.identifier = identifier;
		this.idLinhaProd = idLinhaProd;
		this.idMensagem = idMensagem;
		this.tipoErroNotificacao = tipoErroNotificacao;
		this.estadoErro = estadoErro;
	}

	@Override
	public String toString() {
		return "Notificação Erro " + identifier + ": \n" +
				"  Linha de Produção: " + idLinhaProd + "\n" +
				"  ID de Mensagem: " + idMensagem + "\n" +
				"  Tipo de Erro: " + tipoErroNotificacao + "\n" +
				"  Estado do Erro: " + estadoErro;
	}
}
