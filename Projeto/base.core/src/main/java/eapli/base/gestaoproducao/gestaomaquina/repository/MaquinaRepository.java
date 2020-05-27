package eapli.base.gestaoproducao.gestaomaquina.repository;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.IdentificadorProtocoloComunicacao;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.framework.domain.repositories.DomainRepository;
import java.util.List;
import java.util.Optional;

public interface MaquinaRepository extends DomainRepository<CodigoInternoMaquina, Maquina> {

    /**
     * procura uma máquina pelo identificdor
     * @param identifier identificador da maquina que está a procura
     * @return máquina ou null
     */
    Optional<Maquina> findByIdentifier(CodigoInternoMaquina identifier);

    Iterable<Maquina> findByLinhaProducao(IdentificadorLinhaProducao o);

    Optional<Maquina> findByidentificadorProtocoloComunicacao(IdentificadorProtocoloComunicacao identifier);

	/**
	 * @return a list with all maquinas
	 */
	List<Maquina> findAllList();

	List<Maquina> maquinasSemFicheiroDeConfiguracao();

}
