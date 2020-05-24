package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaMaquinaRepository extends JpaAutoTxRepository<Maquina, CodigoInternoMaquina, CodigoInternoMaquina>
        implements MaquinaRepository {

    public JpaMaquinaRepository(TransactionalContext autoTx) {
        super(autoTx, Maquina.identityAttributeName());
    }

    public JpaMaquinaRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                Maquina.identityAttributeName());
    }

    @Override
    public Optional<Maquina> findByIdentifier(CodigoInternoMaquina identifier) {
        final Map<String, Object> params = new HashMap<>();
        params.put(Maquina.identityAttributeName(), identifier);
        return matchOne("e."+ Maquina.identityAttributeName()+"=:"+Maquina.identityAttributeName(), params);
    }

    /**
     * encontra as máquinas todas numa linha de produção
     * @param linhaProducao a procurar
     * @return Iterable de máquinas numa linha de produção
     */
    public Iterable<Maquina> findByLinhaProducao(IdentificadorLinhaProducao linhaProducao){
        final Map<String, Object> params = new HashMap<>();
        params.put("linhaProducao", linhaProducao);
        return match("e.linhaProducao.identifier=:linhaProducao",params);
    }

    @Override
    public List<Maquina> findAllList() {
        return Lists.newArrayList(this.findAll());
    }

}
