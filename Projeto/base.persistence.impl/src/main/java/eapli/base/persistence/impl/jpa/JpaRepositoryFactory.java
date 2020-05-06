package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.definircategoriamaterial.repository.CategoriaRepository;
import eapli.base.gestaodepositos.repository.DepositoRepository;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.producao.materiaprima.produto.persistence.ProdutoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 *
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

	@Override
	public UserRepository users(final TransactionalContext autoTx) {
		return new JpaAutoTxUserRepository(autoTx);
	}

	@Override
	public UserRepository users() {
		return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
				Application.settings().getExtendedPersistenceProperties());
	}


	@Override
	public JpaClientUserRepository clientUsers(final TransactionalContext autoTx) {
		return new JpaClientUserRepository(autoTx);
	}

	@Override
	public JpaClientUserRepository clientUsers() {
		return new JpaClientUserRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public SignupRequestRepository signupRequests(final TransactionalContext autoTx) {
		return new JpaSignupRequestRepository(autoTx);
	}

	@Override
	public SignupRequestRepository signupRequests() {
		return new JpaSignupRequestRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public ProdutoRepository produto() {
		return new JpaProdutoRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public ProdutoRepository produto(final TransactionalContext autoTx) {
		return new JpaProdutoRepository(autoTx);
	}

	@Override
	public LinhaProducaoRepository linhasProducao() {
		return new JpaLinhaProducaoRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public LinhaProducaoRepository linhasProducao(TransactionalContext autoTx) {
		return new JpaLinhaProducaoRepository(autoTx);
	}

	@Override
	public CategoriaRepository categoria() {
		return new JpaCategoriaRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public CategoriaRepository categoria(TransactionalContext autoTx) {
		return new JpaCategoriaRepository(autoTx);
	}
	public DepositoRepository depositos() {
		return new JpaDepositoRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public DepositoRepository depositos(TransactionalContext autoTx) {
		return new JpaDepositoRepository(autoTx);
	}

	@Override
	public TransactionalContext newTransactionalContext() {
		return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
				Application.settings().getExtendedPersistenceProperties());
	}
}
