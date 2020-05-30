package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.ordemProducao.domain.Identificador;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.processamentoMensagens.domain.AgendamentoDeProcessamento;
import eapli.base.processamentoMensagens.repositories.AgendamentoDeProcessamentoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
import java.util.List;

public class JpaAgendamentoDeProcessamentoRepository extends JpaAutoTxRepository<AgendamentoDeProcessamento,Long,Long> implements AgendamentoDeProcessamentoRepository {

    public JpaAgendamentoDeProcessamentoRepository(TransactionalContext autoTx) {
        super(autoTx, Material.identityAttributeName());
    }

    public JpaAgendamentoDeProcessamentoRepository(String persistenceUnitName) {
        super(persistenceUnitName, Application.settings().getExtendedPersistenceProperties(),
                AgendamentoDeProcessamento.identityAttributeName());
    }

    @Override
    public List<AgendamentoDeProcessamento> findAllList() {
        return Lists.newArrayList(this.findAll());
    }

    @Override
    public List<AgendamentoDeProcessamento> obterAgendamentosPorLinhaDeProducao(LinhaProducao idlinhaProducao) {
        TypedQuery<AgendamentoDeProcessamento> tq = this.createQuery("SELECT distinct e FROM AgendamentoDeProcessamento e where e.linhasProducao = ?0", AgendamentoDeProcessamento.class);
        tq.setParameter(0, idlinhaProducao);
        return tq.getResultList();
    }


}
