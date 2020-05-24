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
	 * @return
	 */
	TransactionalContext newTransactionalContext();

	/**
	 *
	 * @param autoTx the transactional context to enrol
	 * @return
	 */
	UserRepository users(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	UserRepository users();

	/**
	 *
	 * @param autoTx the transactional context to enroll
	 * @return
	 */
	ClientUserRepository clientUsers(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	ClientUserRepository clientUsers();

	/**
	 *
	 * @param autoTx the transactional context to enroll
	 * @return
	 */
	SignupRequestRepository signupRequests(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	SignupRequestRepository signupRequests();

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	ProdutoRepository produto();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return
	 */
	ProdutoRepository produto(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	LinhaProducaoRepository linhasProducao();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return
	 */
	LinhaProducaoRepository linhasProducao(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	CategoriaRepository categoria();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return
	 */
	CategoriaRepository categoria(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
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
	 * @return
	 */
	DepositoRepository depositos(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	FichaDeProducaoRepository fichaDeProducao();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return
	 */
	FichaDeProducaoRepository fichaDeProducao(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	MaterialRepository material();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return
	 */
	MaterialRepository material(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */

	MaquinaRepository maquinas();

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return
	 */
	MaquinaRepository maquinas(TransactionalContext autoTx);
}
