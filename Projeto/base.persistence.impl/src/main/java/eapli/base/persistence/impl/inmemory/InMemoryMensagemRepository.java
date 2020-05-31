package eapli.base.persistence.impl.inmemory;

import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryMensagemRepository extends InMemoryDomainRepository<Long, Mensagem> implements MensagemRepository {
}
