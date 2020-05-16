package eapli.base.persistence.impl.inmemory;

import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.persistence.FichaDeProducaoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryFichaDeProducaoRepository  extends InMemoryDomainRepository<Integer, FichaDeProducao> implements FichaDeProducaoRepository  {

}
