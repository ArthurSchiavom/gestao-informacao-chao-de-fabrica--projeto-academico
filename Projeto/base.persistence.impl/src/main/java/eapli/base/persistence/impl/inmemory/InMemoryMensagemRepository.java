package eapli.base.persistence.impl.inmemory;

import com.google.common.collect.Lists;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;

public class InMemoryMensagemRepository extends InMemoryDomainRepository<Long, Mensagem> implements MensagemRepository {
    @Override
    public List<Mensagem> obterListaMensagensNaoProcessadas() {
        return null;
    }

    @Override
    public List<Mensagem> findAllList() {
        return Lists.newArrayList(this.findAll());
    }
}
