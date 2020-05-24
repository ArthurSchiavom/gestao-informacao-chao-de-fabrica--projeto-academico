/**
 *
 */
package eapli.base.infrastructure.persistence;

import eapli.base.clientusermanagement.repositories.ClientUserRepository;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.gestaoproducao.gestaodeposito.repository.DepositoRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomaterial.repository.CategoriaRepository;
import eapli.base.gestaoproducao.gestaomaterial.repository.MaterialRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.FichaDeProducaoRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

public interface RepositoryFactory {

	/**
	 * factory method to create a transactional context to use in the repositories
	 *
	 * @return transactional context for use in repos
	 */
	TransactionalContext newTransactionalContext();

	/**
	 *
	 * @param autoTx the transactional context to enrol
	 * @return the repository with a certain transactional context
	 */
	UserRepository users(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return repository automatic transaction mode
	 */
	UserRepository users();

	/**
	 *
	 * @param autoTx the transactional context to enroll
	 * @return the repository with a certain transactional context
	 */
	ClientUserRepository clientUsers(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return repository automatic transaction mode
	 */
	ClientUserRepository clientUsers();

	/**
	 *
	 * @param autoTx the transactional context to enroll
	 * @return the repository with a certain transactional context
	 */
	SignupRequestRepository signupRequests(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return repository automatic transaction mode
	 */
	SignupRequestRepository signupRequests();

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return repository automatic transaction mode
	 */
	ProdutoRepository produto();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return the repository with a certain transactional context
	 */
	ProdutoRepository produto(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return repository automatic transaction mode
	 */
	LinhaProducaoRepository linhasProducao();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return the repository with a certain transactional context
	 */
	LinhaProducaoRepository linhasProducao(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return repository automatic transaction mode
	 */
	CategoriaRepository categoria();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return the repository with a certain transactional context
	 */
	CategoriaRepository categoria(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return repository automatic transaction mode
	 */
	OrdemProducaoRepository ordemProducao();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return
	 */
	OrdemProducaoRepository ordemProducao(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	DepositoRepository depositos();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return the repository with a certain transactional context
	 */
	DepositoRepository depositos(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return repository automatic transaction mode
	 */
	FichaDeProducaoRepository fichaDeProducao();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return the repository with a certain transactional context
	 */
	FichaDeProducaoRepository fichaDeProducao(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return repository automatic transaction mode
	 */
	MaterialRepository material();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return the repository with a certain transactional context
	 */
	MaterialRepository material(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return repository automatic transaction mode
	 */

	MaquinaRepository maquinas();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return the repository with a certain transactional context
	 */
	MaquinaRepository maquinas(TransactionalContext autoTx);
}
