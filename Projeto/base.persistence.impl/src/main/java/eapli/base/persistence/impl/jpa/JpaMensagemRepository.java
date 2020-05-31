package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;


public class JpaMensagemRepository extends JpaAutoTxRepository<Mensagem,Long,Long> implements MensagemRepository {

    public JpaMensagemRepository(TransactionalContext autoTx) {
        super(autoTx, Mensagem.identityAttributeName());
    }

    public JpaMensagemRepository(String persistenceUnitName) {
        super(persistenceUnitName, Application.settings().getExtendedPersistenceProperties(),
                Mensagem.identityAttributeName());
    }
}
