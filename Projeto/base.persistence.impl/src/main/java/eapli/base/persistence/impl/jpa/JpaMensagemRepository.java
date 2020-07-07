package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaomensagens.domain.EstadoProcessamento;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemID;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
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
                "SELECT e FROM Mensagem e WHERE e.mensagemID.tempoEmissao.timestamp >= ?1", Mensagem.class);
        tq.setParameter(1, dataAFiltrar);
        return tq.getResultList();
    }

    @Override
    public List<Mensagem> listaMensagensNaoProcessadas() {
        TypedQuery<Mensagem> tq = this.createQuery("SELECT m FROM Mensagem m WHERE m.estadoProcessamento = ?1", Mensagem.class);
        tq.setParameter(1, EstadoProcessamento.NAO_PROCESSADO);
        return tq.getResultList();
    }

    @Override
    public void enriquecerMensagensComLinhaProducao() {
        TypedQuery<Mensagem> tq = this.createQuery(
                " UPDATE Mensagem " +
                        " SET identificadorLinhaProducao = (SELECT m.linhaProducao FROM Maquina m " +
                        " WHERE identificadorLinhaProducao IS NULL " +
                        " AND mensagemID.codigoInternoMaquina = m.codigoInternoMaquina) "
                , Mensagem.class);
        tq.executeUpdate();
    }

}
