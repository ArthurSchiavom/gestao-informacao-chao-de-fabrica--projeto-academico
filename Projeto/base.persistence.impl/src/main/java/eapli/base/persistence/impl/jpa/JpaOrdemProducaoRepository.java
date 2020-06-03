package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.ordemProducao.domain.Estado;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
import java.util.*;

public class JpaOrdemProducaoRepository extends JpaAutoTxRepository<OrdemProducao, IdentificadorOrdemProducao,
        IdentificadorOrdemProducao>
		implements OrdemProducaoRepository {

	public JpaOrdemProducaoRepository(TransactionalContext autoTx) {
		super(autoTx, Maquina.identityAttributeName());
	}

	public JpaOrdemProducaoRepository(String puname) {
		super(puname, Application.settings().getExtendedPersistenceProperties(),
				OrdemProducao.identityAttributeName());
	}

	@Override
	public Optional<OrdemProducao> findByIdentifier(IdentificadorOrdemProducao identifier) {
		final Map<String, Object> params = new HashMap<>();
		params.put(OrdemProducao.identityAttributeName(), identifier);
		return matchOne("e." + OrdemProducao.identityAttributeName() + "=:" + OrdemProducao.identityAttributeName(),
				params);
	}

	@Override
	public OrdemProducao findOrdemProducaoByEncomenda(String id) {
		TypedQuery<OrdemProducao> tq = this.createQuery("SELECT distinct e FROM OrdemProducao e JOIN " +
				"e.identificadorEncomendaList ef where ef.identificador = ?0", OrdemProducao.class);
		tq.setParameter(0, id);

		return tq.getSingleResult();

	}

	@Override
	public Iterable<OrdemProducao> findOrdemProducaoByEstado(Estado estado) {
		final Map<String, Object> params = new HashMap<>();
		params.put("estado", estado);
		return match("e.estado=:estado", params);
	}

	/**
	 * Guarda a ordem de produção mesmo que já exista uma ordem de produção com a mesma chave primária
	 */
	@Override
	public boolean saveRewrite(OrdemProducao op) {
		if (op == null) {
			return false;
		}
		RepositoryFactory repositoryFactory = PersistenceContext.repositories();
		TransactionalContext transactionalContext = repositoryFactory.newTransactionalContext();
		OrdemProducaoRepository opRepository = repositoryFactory.ordemProducao();

		transactionalContext.beginTransaction(); // begin transaction

		Optional<OrdemProducao> antigo = opRepository.findByIdentifier(op.identificador);

		if (antigo.isPresent()) {
			OrdemProducao opAntigo = antigo.get();
			opRepository.remove(opAntigo);
		}

		try {
			opRepository.save(op);

			transactionalContext.commit(); // if it saves correctly, commit and save
			transactionalContext.close();

			return true;

		} catch (Exception ex) { // in case of an error Rollback
			transactionalContext.rollback();
			transactionalContext.close();
			throw ex;
		}
	}

	@Override
	public List<OrdemProducao> findAllwithDateAfter(Date dataFiltrar) {
		TypedQuery<OrdemProducao> tq = this.createQuery(
				"SELECT e FROM OrdemProducao e WHERE e.dataEmissao >= ?0", OrdemProducao.class);
		tq.setParameter(0, dataFiltrar);
		return tq.getResultList();
	}
}
