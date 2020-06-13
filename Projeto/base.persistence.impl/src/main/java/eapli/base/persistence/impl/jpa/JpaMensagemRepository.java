package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomensagens.domain.EstadoProcessamento;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemID;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.processamentoMensagens.domain.AgendamentoDeProcessamento;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;


public class JpaMensagemRepository extends JpaAutoTxRepository<Mensagem, MensagemID,MensagemID> implements MensagemRepository {

    public JpaMensagemRepository(TransactionalContext autoTx) {
        super(autoTx, Mensagem.identityAttributeName());
    }

    public JpaMensagemRepository(String persistenceUnitName) {
        super(persistenceUnitName, Application.settings().getExtendedPersistenceProperties(),
                Mensagem.identityAttributeName());
    }


    @Override
    public List<Mensagem> findAllList() {
        return Lists.newArrayList(this.findAll());
    }

    @Override
    public List<Mensagem> findAllWithDateAfter(Date dataAFiltrar) {
        TypedQuery<Mensagem> tq = this.createQuery(
                "SELECT e FROM Mensagem e WHERE e.mensagemID.tempoEmissao.timestamp >= ?0", Mensagem.class);
        tq.setParameter(0, dataAFiltrar);
        return tq.getResultList();
    }

    @Override
    public List<Mensagem> obterListaMensagensNaoProcessadas() {
        TypedQuery<Mensagem> tq = this.createQuery("SELECT m FROM Mensagem m WHERE m.estadoProcessamento = ?0", Mensagem.class);
        tq.setParameter(0,EstadoProcessamento.NAO_PROCESSADO );
        return tq.getResultList();
    }
}
