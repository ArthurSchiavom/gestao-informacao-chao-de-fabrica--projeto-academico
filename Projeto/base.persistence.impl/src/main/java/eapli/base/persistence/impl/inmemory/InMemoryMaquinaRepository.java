package eapli.base.persistence.impl.inmemory;

import com.google.common.collect.Lists;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryMaquinaRepository extends InMemoryDomainRepository<CodigoInternoMaquina, Maquina>
        implements MaquinaRepository {
    @Override
    public Optional<Maquina> findByIdentifier(CodigoInternoMaquina identifier) {
        return Optional.empty();
    }

    /**
     * Por implementar
     */
    @Override
    public Iterable<Maquina> findByLinhaProducao(IdentificadorLinhaProducao linhaProducao) {
        return null;

    }

    @Override
    public List<Maquina> findAllList() {
        return Lists.newArrayList(this.findAll());
    }
}
