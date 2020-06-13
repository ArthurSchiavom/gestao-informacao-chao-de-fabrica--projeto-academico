package eapli.base.processamentoMensagens.repositories;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.processamentoMensagens.domain.AgendamentoDeProcessamento;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Date;
import java.util.List;

public interface AgendamentoDeProcessamentoRepository extends DomainRepository<Long, AgendamentoDeProcessamento> {
    List<AgendamentoDeProcessamento> findAllList();
    List<AgendamentoDeProcessamento> obterAgendamentosPorLinhaDeProducao(LinhaProducao idlinhaProducao);

	List<AgendamentoDeProcessamento> findAllWithDateAfter(Date dataAFiltrar);
}
