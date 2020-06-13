package eapli.base.processamentoMensagens.application.tiposMensagensNotificacao;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemFimDeAtividade;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;

import java.util.Date;

public class MensagemFimDeAtividadeNotificacao implements ValidadorMensagem {
	@Override
	public NotificacaoErro validarMensagem(MensagemRepository mensagemRepository, Mensagem mensagem, ValidacaoParametrosMensagensServico validacao) {
		MensagemFimDeAtividade mensagemFimDeAtividade = (MensagemFimDeAtividade) mensagem;
		Date dataEmissao = mensagemFimDeAtividade.mensagemID.tempoEmissao.timestamp;
		IdentificadorOrdemProducao idOrdemProducao = mensagemFimDeAtividade.getIdentificadorOrdemDeProducao();
		OrdemProducao ordemProducao;

		TipoErroNotificacao DADOS_INVALIDOS = TipoErroNotificacao.DADOS_INVALIDOS;
		TipoErroNotificacao ELEMENTOS_INEXISTENTES = TipoErroNotificacao.ELEMENTOS_INEXISTENTES;

		//DATA
		if (!validacao.validarData(dataEmissao))
			return NotificacaoErro.gerarNotificacaoDeErro(DADOS_INVALIDOS, mensagemRepository, mensagem);
		//IDENTIFICADOR DE ORDEM DE PRODUCAO
		if (idOrdemProducao != null) {
			ordemProducao = validacao.getOrdemDeProducaoPorIdentificador(idOrdemProducao);
			if (ordemProducao == null)
				return NotificacaoErro.gerarNotificacaoDeErro(ELEMENTOS_INEXISTENTES, mensagemRepository, mensagem);
		}
		return null;
	}
}
