package eapli.base.gestaoproducao.gestaomensagens.repository;

import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemID;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Date;
import java.util.List;

public interface MensagemRepository extends DomainRepository<MensagemID, Mensagem> {
    List<Mensagem> listaMensagensNaoProcessadas();
    List<Mensagem> findAllList() ;

	List<Mensagem> findAllWithDateAfter(Date dataAFiltrar);
	void enriquecerMensagensComLinhaProducao();
}
