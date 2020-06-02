package eapli.base.gestaoproducao.gestaomensagens.repository;

import com.google.common.collect.Lists;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemID;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;

public interface MensagemRepository extends DomainRepository<MensagemID, Mensagem> {
    List<Mensagem> obterListaMensagensNaoProcessadas();
    List<Mensagem> findAllList() ;
}
