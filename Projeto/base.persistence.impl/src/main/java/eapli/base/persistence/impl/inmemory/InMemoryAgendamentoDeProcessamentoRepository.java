package eapli.base.persistence.impl.inmemory;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.processamentoMensagens.domain.AgendamentoDeProcessamento;
import eapli.base.processamentoMensagens.repositories.AgendamentoDeProcessamentoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;

public class InMemoryAgendamentoDeProcessamentoRepository extends InMemoryDomainRepository<Long, AgendamentoDeProcessamento> implements AgendamentoDeProcessamentoRepository {


    @Override
    public List<AgendamentoDeProcessamento> findAllList() {
        return null;
    }

    @Override
    public List<AgendamentoDeProcessamento> obterAgendamentosPorLinhaDeProducao(LinhaProducao idlinhaProducao) {
        return null;
    }
}
