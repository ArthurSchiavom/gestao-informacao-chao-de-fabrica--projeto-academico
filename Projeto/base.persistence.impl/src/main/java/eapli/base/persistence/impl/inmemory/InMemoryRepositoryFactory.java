package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.repositories.ClientUserRepository;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.gestaoproducao.gestaodeposito.repository.DepositoRepository;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomaterial.repository.CategoriaRepository;
import eapli.base.gestaoproducao.gestaomaterial.repository.MaterialRepository;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.FichaDeProducaoRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.bootstrapers.BaseBootstrapper;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.processamentoMensagens.repositories.AgendamentoDeProcessamentoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.InMemoryUserRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

	static {
		// only needed because of the in memory persistence
		new BaseBootstrapper().execute();
	}

	@Override
	public UserRepository users(final TransactionalContext tx) {
		return new InMemoryUserRepository();
	}

	@Override
	public UserRepository users() {
		return users(null);
	}


	@Override
	public ClientUserRepository clientUsers(final TransactionalContext tx) {

		return new InMemoryClientUserRepository();
	}

	@Override
	public ClientUserRepository clientUsers() {
		return clientUsers(null);
	}

	@Override
	public SignupRequestRepository signupRequests() {
		return signupRequests(null);
	}

	@Override
	public ProdutoRepository produto() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ProdutoRepository produto(TransactionalContext autoTx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public LinhaProducaoRepository linhasProducao() {
		return linhasProducao(null);
	}

	@Override
	public LinhaProducaoRepository linhasProducao(final TransactionalContext autoTx) {
		return new InMemoryLinhaProducaoRepository();
	}

	@Override
	public CategoriaRepository categoria() {
		return this.categoria(null);
	}

	@Override
	public CategoriaRepository categoria(final TransactionalContext autoTx) {
		return new InMemoryCategoriaRepository();
	}
	public DepositoRepository depositos() {
		return depositos(null);
	}

	@Override
	public DepositoRepository depositos(final TransactionalContext autoTx) {
		return new InMemoryDepositRepository();
	}

	@Override
	public MaquinaRepository maquinas() {
		return maquinas(null);
	}

	@Override
	public MaquinaRepository maquinas(TransactionalContext autoTx) {
		return maquinas(autoTx);
	}

	@Override
	public NotificacaoErroRepository notificacoesErros() {
		return notificacoesErros(null);
	}

	@Override
	public NotificacaoErroRepository notificacoesErros(TransactionalContext autoTx) {
		return notificacoesErros(autoTx);
	}

	@Override
	public OrdemProducaoRepository ordemProducao() {
		return ordemProducao(null);
	}

	@Override
	public OrdemProducaoRepository ordemProducao(TransactionalContext autoTx) {
		return ordemProducao(autoTx);
	}


	public FichaDeProducaoRepository fichaDeProducao() {
		return new InMemoryFichaDeProducaoRepository();
	}

	@Override
	public FichaDeProducaoRepository fichaDeProducao(TransactionalContext autoTx) {
		return new InMemoryFichaDeProducaoRepository();
	}

	@Override
	public MaterialRepository material() {
		return material(null);
	}

	@Override
	public MaterialRepository material(final TransactionalContext autoTx) {
		return new InMemoryMaterialRepository();
	}

	@Override
	public SignupRequestRepository signupRequests(final TransactionalContext tx) {
		return new InMemorySignupRequestRepository();
	}

	@Override
	public TransactionalContext newTransactionalContext() {
		// in memory does not support transactions...
		return null;
	}

	@Override
	public AgendamentoDeProcessamentoRepository agendamentoDeProcessamento() {
		return new InMemoryAgendamentoDeProcessamentoRepository();
	}

	@Override
	public AgendamentoDeProcessamentoRepository agendamentoDeProcessamento(TransactionalContext autoTx) {
		return agendamentoDeProcessamento(autoTx);
	}

	@Override
	public MensagemRepository mensagem() {
		return new InMemoryMensagemRepository();
	}

	@Override
	public MensagemRepository mensagem(TransactionalContext autoTx) {
		return mensagem(autoTx);
	}

}
