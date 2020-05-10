package eapli.base.persistence.impl.inmemory;

import com.google.common.collect.Lists;
import eapli.base.definircategoriamaterial.domain.CodigoInterno;
import eapli.base.definircategoriamaterial.domain.Material;
import eapli.base.produto.domain.FichaDeProducao;
import eapli.base.produto.persistence.FichaDeProducaoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;

public class InMemoryFichaDeProducaoRepository  extends InMemoryDomainRepository<Integer, FichaDeProducao> implements FichaDeProducaoRepository  {

}
