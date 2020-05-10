/**
 *
 */
package eapli.base.infrastructure.persistence;

import eapli.base.clientusermanagement.repositories.ClientUserRepository;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.definircategoriamaterial.repository.CategoriaRepository;
import eapli.base.definircategoriamaterial.repository.MaterialRepository;
import eapli.base.gestaodepositos.repository.DepositoRepository;
import eapli.base.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.produto.persistence.FichaDeProducaoRepository;
<<<<<<< Updated upstream
import eapli.base.registarmaquina.repository.MaquinaRepository;
import eapli.base.gestaomateriasprimas.repository.MaterialRepository;
=======
>>>>>>> Stashed changes
import eapli.base.produto.persistence.ProdutoRepository;
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
<<<<<<< Updated upstream

	MaquinaRepository maquinas();
=======
	FichaDeProducaoRepository fichaDeProducao();
>>>>>>> Stashed changes

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return
	 */
<<<<<<< Updated upstream
	MaquinaRepository maquinas(TransactionalContext autoTx);

	MaterialRepository material();

	/**
	 *
	 * @param autoTx the transactional context to enroll
	 * @return
	 */
	MaterialRepository material(TransactionalContext autoTx);
=======
	FichaDeProducaoRepository fichaDeProducao(TransactionalContext autoTx);
>>>>>>> Stashed changes

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
<<<<<<< Updated upstream
	FichaDeProducaoRepository fichaDeProducao();
=======
	MaterialRepository material();
>>>>>>> Stashed changes

	/**
	 * @param autoTx the transactional context to enroll
	 *
	 * @return
	 */
<<<<<<< Updated upstream
	FichaDeProducaoRepository fichaDeProducao(TransactionalContext autoTx);
=======
	MaterialRepository material(TransactionalContext autoTx);
>>>>>>> Stashed changes
}
