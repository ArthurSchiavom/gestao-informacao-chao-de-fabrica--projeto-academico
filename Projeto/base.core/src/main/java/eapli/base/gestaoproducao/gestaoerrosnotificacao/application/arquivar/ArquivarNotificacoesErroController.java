package eapli.base.gestaoproducao.gestaoerrosnotificacao.application.arquivar;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.ServicoArquivacaoNotificacoesErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.NotificacaoErroDTO;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.infrastructure.application.DTOUtils;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.util.Collections;
import java.util.List;

public class ArquivarNotificacoesErroController {
	private final ServicoArquivacaoNotificacoesErro servico;
	private List<NotificacaoErro> listaNotificacoesErros;

	public ArquivarNotificacoesErroController() {
		NotificacaoErroRepository notifErroRepo = PersistenceContext.repositories().notificacoesErros();
		servico = new ServicoArquivacaoNotificacoesErro(notifErroRepo);
		listaNotificacoesErros = notifErroRepo.findAllNaoArquivados();
	}

	public List<NotificacaoErroDTO> notificacoesErroNaoArquivadas() {
		return Collections.unmodifiableList(DTOUtils.toDTOList(listaNotificacoesErros));
	}

	public void arquivarNotificacao(Long idNotificacao) {
		NotificacaoErro notifAtualizada = servico.arquivar(idNotificacao);
		listaNotificacoesErros.remove(notifAtualizada);
	}
}
