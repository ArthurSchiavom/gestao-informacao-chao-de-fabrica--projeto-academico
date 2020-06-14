package eapli.base.gestaoproducao.gestaoerrosnotificacao.repository;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.EstadoNotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemID;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;

public interface NotificacaoErroRepository extends DomainRepository<Long, NotificacaoErro> {
	/**
	 * Encontra todas as notificações de erros de processamento filtradas por tipo de erro
	 * e por linha de produção
	 *
	 * @param tipo        o tipo de erro pelo qual pretendemos filtrar
	 * @param idLinhaProd o id da linha de produção pela qual pretendemos filtrar
	 * @return uma lista com todas as notificações dos erros de processamento que correspondam
	 * aos filtros introduzidos
	 */
	List<NotificacaoErro> findAllPorTratarfilteredByTipoErroAndLinhaProd(TipoErroNotificacao tipo,
	                                                                     IdentificadorLinhaProducao idLinhaProd);

	/**
	 * Encontra todas as notificações de erro de processamento filtradas por não estarem arquivadas
	 *
	 * @return uma lista com notificações de erro não filtradas
	 */
	List<NotificacaoErro> findAllNaoArquivados();

    List<NotificacaoErro> findAll(List<String> idsLinhasProducaoSelecionadas, List<TipoErroNotificacao> tiposNotificaoErroSelecionados, List<EstadoNotificacaoErro> estadoErroNotificacaosSelecionados);

	boolean mensagemTemErroPorTratar(MensagemID mensagemID);
}
