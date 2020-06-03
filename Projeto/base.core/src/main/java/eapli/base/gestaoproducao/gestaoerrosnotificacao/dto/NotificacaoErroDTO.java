package eapli.base.gestaoproducao.gestaoerrosnotificacao.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificacaoErroDTO {
	public final Long identifier;
	public final String idLinhaProd;
	public final String tipoErroNotificacao;
	public final String estadoErro;
	public final Date dataEmissaoMensagem;
	public final String tipoMensagem;
	public final String maquina;

	public NotificacaoErroDTO(Long identifier, String idLinhaProd, String tipoErroNotificacao, String estadoErro, Date dataEmissaoMensagem, String tipoMensagem, String maquina) {
		this.identifier = identifier;
		this.idLinhaProd = idLinhaProd;
		this.tipoErroNotificacao = tipoErroNotificacao;
		this.estadoErro = estadoErro;
		this.dataEmissaoMensagem = dataEmissaoMensagem;
		this.tipoMensagem = tipoMensagem;
		this.maquina = maquina;
	}

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return "Notificação Erro: " + identifier + "\n" +
				"Linha de Produção: " + idLinhaProd + "\n" +
				"Tipo de Erro: " + tipoErroNotificacao + "\n" +
				"Estado do Erro: " + estadoErro + "\n" +
				"Data de Emissão da Mensagem: " + dateFormat.format(dataEmissaoMensagem) + "\n" +
				"Tipo de Mensagem: " + tipoMensagem + "\n" +
				"Máquina: " + maquina;
	}
}
