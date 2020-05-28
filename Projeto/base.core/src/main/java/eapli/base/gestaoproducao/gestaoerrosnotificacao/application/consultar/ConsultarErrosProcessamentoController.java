package eapli.base.gestaoproducao.gestaoerrosnotificacao.application.consultar;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.NotificacaoErroDTO;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.infrastructure.application.DTOUtils;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConsultarErrosProcessamentoController {
	private final List<LinhaProducaoDTO> linhasProdDTO;
	private IdentificadorLinhaProducao lProd;
	private TipoErroNotificacao tipoErro;
	private List<TipoErroNotificacao> tiposErroNotificacao;

	public ConsultarErrosProcessamentoController() {
		LinhaProducaoRepository lProdRepo = PersistenceContext.repositories().linhasProducao();
		List<LinhaProducao> linhasProducao = lProdRepo.findAllList();
		linhasProdDTO = Collections.unmodifiableList(DTOUtils.toDTOList(linhasProducao));
	}

	public List<LinhaProducaoDTO> linhasProducao() {
		return linhasProdDTO;
	}

	public void definirFiltroLinhaProducao(int linhaProdOpcao) {
		lProd = new IdentificadorLinhaProducao(linhasProdDTO.get(linhaProdOpcao).identificadorLinhaProducao);
	}

	public List<TipoErroNotificacao> tiposErro() {
		tiposErroNotificacao = Collections.unmodifiableList(Arrays.asList(TipoErroNotificacao.values()));
		return tiposErroNotificacao;
	}

	public void definirTipoErro(int opcaoTipoErro) {
		this.tipoErro = tiposErroNotificacao.get(opcaoTipoErro);
	}

	public List<NotificacaoErroDTO> listarErrosProcessamento() {
		NotificacaoErroRepository notifErroRepo = PersistenceContext.repositories().notificacoesErros();
		List<NotificacaoErro> listNotifErros = notifErroRepo.findAllNaoArquivadosfilteredByTipoErroAndLinhaProd(
				tipoErro, lProd);
		return Collections.unmodifiableList(DTOUtils.toDTOList(listNotifErros));
	}
}
