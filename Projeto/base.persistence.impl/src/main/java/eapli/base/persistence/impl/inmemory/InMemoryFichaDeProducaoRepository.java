package eapli.base.persistence.impl.inmemory;

import eapli.base.gestaoproducao.gestaoprodutos.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoprodutos.persistence.FichaDeProducaoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryFichaDeProducaoRepository  extends InMemoryDomainRepository<Integer, FichaDeProducao> implements FichaDeProducaoRepository  {

}
