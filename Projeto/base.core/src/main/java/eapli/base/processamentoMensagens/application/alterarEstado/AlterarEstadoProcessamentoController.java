package eapli.base.processamentoMensagens.application.alterarEstado;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.EstadoProcessamentoMensagens;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.infrastructure.application.DTOUtils;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.TransactionalContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AlterarEstadoProcessamentoController {
	private final TransactionalContext context = PersistenceContext.repositories().newTransactionalContext();
	private final LinhaProducaoRepository lprodRepo = PersistenceContext.repositories().linhasProducao(context);
	private List<LinhaProducao> linhasProducao;
	private LinhaProducao lProd;
	private List<EstadoProcessamentoMensagens> estadosProcessamento;
	private EstadoProcessamentoMensagens estadoPretendido;

	public List<LinhaProducaoDTO> linhasProducao(int pageSize, int pagina) {
		linhasProducao = lprodRepo.findPage(pageSize, pagina);
		return Collections.unmodifiableList(DTOUtils.toDTOList(linhasProducao));
	}

	public LinhaProducaoDTO definirLinhaProducao(int opcaoLinhaProd) {
		lProd = linhasProducao.get(opcaoLinhaProd);
		return lProd.toDTO();
	}

	public List<EstadoProcessamentoMensagens> estadosProcessamento() {
		estadosProcessamento = Arrays.asList(EstadoProcessamentoMensagens.values());
		return Collections.unmodifiableList(estadosProcessamento);
	}

	public boolean escolherEstado(int opcaoEstado) {
		estadoPretendido = estadosProcessamento.get(opcaoEstado);
		return lProd.podeMudarParaEstado(estadoPretendido);
	}

	public boolean alterarEstado() {
		boolean result = lProd.alterarEstado(estadoPretendido);
		context.beginTransaction();
		lprodRepo.save(lProd);
		context.commit();
		return result;
	}

	public boolean podeEfetuarReprocessamento() {
		return EstadoProcessamentoMensagens.podeEfetuarReprocessamento(estadoPretendido);
	}
}
