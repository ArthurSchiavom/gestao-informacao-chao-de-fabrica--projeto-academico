package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.Repository.ProdutoProduzidoRepository;
import eapli.base.gestaoproducao.gestaodeposito.repository.DepositoRepository;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomaterial.repository.CategoriaRepository;
import eapli.base.gestaoproducao.gestaomaterial.repository.MaterialRepository;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.FichaDeProducaoRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.movimentos.repositoy.MovimentoStockRepository;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.indicarUsoDeMaquina.repositories.UsoDeMaquinaRepository;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.processamentoMensagens.repositories.AgendamentoDeProcessamentoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
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
//		return new InMemoryProdutoRepository();
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

	@Override
	public MaquinaRepository maquinas() {
		return  new JpaMaquinaRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public MaquinaRepository maquinas(TransactionalContext autoTx) {
		return new JpaMaquinaRepository(autoTx);
	}

    @Override
    public NotificacaoErroRepository notificacoesErros() {
        return new JpaNotificacoesErroRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public NotificacaoErroRepository notificacoesErros(TransactionalContext autoTx) {
        return new JpaNotificacoesErroRepository(autoTx);
    }

    @Override
    public OrdemProducaoRepository ordemProducao() {
        return  new JpaOrdemProducaoRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public OrdemProducaoRepository ordemProducao(TransactionalContext autoTx) {
        return new JpaOrdemProducaoRepository(autoTx);
    }


    @Override
	public MaterialRepository material() {
		return new JpaMaterialRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public MaterialRepository material(TransactionalContext autoTx) {
		return new JpaMaterialRepository(autoTx);
	}

    @Override
    public FichaDeProducaoRepository fichaDeProducao() {
        return new JpaFichaDeProducaoRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public FichaDeProducaoRepository fichaDeProducao(TransactionalContext autoTx) {
        return new JpaFichaDeProducaoRepository(autoTx);
    }

    @Override
    public AgendamentoDeProcessamentoRepository agendamentoDeProcessamento() {
        return new JpaAgendamentoDeProcessamentoRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public AgendamentoDeProcessamentoRepository agendamentoDeProcessamento(TransactionalContext autoTx) {
        return new JpaAgendamentoDeProcessamentoRepository(autoTx);
    }

    @Override
    public MensagemRepository mensagem() {
        return new JpaMensagemRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public MensagemRepository mensagem(TransactionalContext autoTx) {
        return new JpaMensagemRepository(autoTx);
    }

    @Override
    public MovimentoStockRepository movimentoStock() {
        return new JpaMovimentoStockRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public MovimentoStockRepository movimentoStock(TransactionalContext autoTx) {
        return new JpaMovimentoStockRepository(autoTx);
    }

    @Override
    public UsoDeMaquinaRepository usoDeMaquina() {
        return new JpaUsoDeMaquinaRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public UsoDeMaquinaRepository usoDeMaquina(TransactionalContext autoTx) {
        return new JpaUsoDeMaquinaRepository(autoTx);
    }

    @Override
    public ProdutoProduzidoRepository produtoProduzido() {
        return new JpaProdutoProduzidoRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public ProdutoProduzidoRepository produtoProduzido(TransactionalContext autoTx) {
        return new JpaProdutoProduzidoRepository(autoTx);
    }
}
