package eapli.base.gestaoproducao.exportacao.domain;

import com.google.common.collect.Lists;
import eapli.base.gestaoproducao.gestaodeposito.domain.Deposito;
import eapli.base.gestaoproducao.gestaodeposito.repository.DepositoRepository;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomaterial.repository.CategoriaRepository;
import eapli.base.gestaoproducao.gestaomaterial.repository.MaterialRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.*;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.FichaDeProducaoRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.movimentos.domain.MovimentoStock;
import eapli.base.gestaoproducao.movimentos.repositoy.MovimentoStockRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.processamentoMensagens.domain.AgendamentoDeProcessamento;
import eapli.base.processamentoMensagens.repositories.AgendamentoDeProcessamentoRepository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * O chão de fábrica contem todos os elementos do dominio
 * pertencentes ao chão de fábrica
 * <p>
 * Para utilizar, utilizar o Builder
 */
@XmlRootElement(name = "chaoDeFabrica")
public class ChaoDeFabrica {
	@XmlElementWrapper(name = "linhasDeProducao")
	@XmlElement(name = "linhaDeProducao")
	private List<LinhaProducao> listaLinhaProd;

	@XmlElementWrapper(name = "depositos")
	@XmlElement(name = "deposito")
	private List<Deposito> listaDepositos;

	@XmlElementWrapper(name = "categorias")
	@XmlElement(name = "categoria")
	private List<Categoria> listaCategoria;

	@XmlElementWrapper(name = "produtos")
	@XmlElement(name = "produto")
	private List<Produto> listaProdutos;

	@XmlElementWrapper(name = "materiais")
	@XmlElement(name = "material")
	private List<Material> listaMateriais;

	@XmlElementWrapper(name = "fichasDeProducao")
	@XmlElement(name = "fichaProducao")
	private List<FichaDeProducao> listaFichasProducao;

	@XmlElementWrapper(name = "maquinas")
	@XmlElement(name = "maquina")
	private List<Maquina> listaMaquinas;

	@XmlElementWrapper(name = "ordensDeProducao")
	@XmlElement(name = "ordemProducao")
	private List<OrdemProducao> listaOrdensProducao;

	@XmlElementWrapper(name = "notificacoesDeErroDeProcessamento")
	@XmlElement(name = "notificacaoDeErroDeProcessamento")
	private List<NotificacaoErro> listaNotificacoesErro;

	@XmlElementWrapper(name = "movimentosStock")
	@XmlElement(name = "movimentoStock")
	private List<MovimentoStock> listaMovimentosStock;

	@XmlElementWrapper(name = "agendamentosDeProcessamento")
	@XmlElement(name = "agendamentoDeProcessamento")
	private List<AgendamentoDeProcessamento> listaAgendamentosDeProcessamento;

	@XmlElementWrapper(name = "mensagens")
	@XmlElements({
			@XmlElement(name = "mensagemInicioDeAtividade", type= MensagemInicioDeAtividade.class),
			@XmlElement(name = "mensagemFimDeAtividade", type = MensagemFimDeAtividade.class),
			@XmlElement(name = "mensagemParagemForcada", type = MensagemParagemForcada.class),
			@XmlElement(name = "mensagemRetomaDeAtividade", type = MensagemRetomoDeActividade.class),
			@XmlElement(name = "mensagemDeConsumo", type = MensagemConsumo.class),
			@XmlElement(name = "mensagemDeProducao", type = MensagemProducao.class),
			@XmlElement(name = "mensagemDeEntregaDeProducao", type = MensagemEntregaDeProducao.class),
			@XmlElement(name = "mensagemDeEstorno", type = MensagemEstorno.class)
	})
	private List<Mensagem> listaMensagens;

	private boolean nothingWasLoaded;

	public ChaoDeFabrica(boolean nothingWasLoaded, List<LinhaProducao> listaLinhaProd, List<Deposito> listaDepositos,
	                     List<Categoria> listaCategoria, List<Produto> listaProdutos,
	                     List<Material> listaMateriais, List<FichaDeProducao> listaFichasProducao,
	                     List<Maquina> listaMaquinas, List<OrdemProducao> listaOrdensProducao,
	                     List<NotificacaoErro> listaNotificacoesErro, List<Mensagem> listaMensagens,
	                     List<MovimentoStock> listaMovimentosStock, List<AgendamentoDeProcessamento> listaAgendamentosDeProcessamento) {
		this.nothingWasLoaded = nothingWasLoaded;
		this.listaLinhaProd = listaLinhaProd;
		this.listaDepositos = listaDepositos;
		this.listaCategoria = listaCategoria;
		this.listaProdutos = listaProdutos;
		this.listaMateriais = listaMateriais;
		this.listaFichasProducao = listaFichasProducao;
		this.listaMaquinas = listaMaquinas;
		this.listaOrdensProducao = listaOrdensProducao;
		this.listaNotificacoesErro = listaNotificacoesErro;
		this.listaMensagens = listaMensagens;
		this.listaMovimentosStock = listaMovimentosStock;
		this.listaAgendamentosDeProcessamento = listaAgendamentosDeProcessamento;
	}

	public ChaoDeFabrica() {
		//For JAXB reasons
	}

	/**
	 * Verifica que pelo menos alguma coisa foi carregada
	 *
	 * @return true se pelo menos uma coisa foi carregada
	 */
	public boolean nothingWasLoaded() {
		return this.nothingWasLoaded;
	}

	public static class Builder {
		private boolean hasLoadedNothing;
		private final RepositoryFactory repoFact;
		private List<LinhaProducao> listaLinhaProd;
		private List<Deposito> listaDepositos;
		private List<Categoria> listaCategoria;
		private List<Produto> listaProdutos;
		private List<Material> listaMateriais;
		private List<FichaDeProducao> listaFichasProducao;
		private List<Maquina> listaMaquinas;
		private List<OrdemProducao> listaOrdensProducao;
		private List<NotificacaoErro> listaNotificacoesErro;
		private List<Mensagem> listaMensagens;
		private List<MovimentoStock> listaMovimentosStock;
		private List<AgendamentoDeProcessamento> listaAgendamentosDeProcessamento;

		public Builder(RepositoryFactory repoFact) {
			this.hasLoadedNothing = true;
			this.repoFact = repoFact;
			this.listaLinhaProd = new ArrayList<>();
			this.listaDepositos = new ArrayList<>();
			this.listaCategoria = new ArrayList<>();
			this.listaProdutos = new ArrayList<>();
			this.listaMateriais = new ArrayList<>();
			this.listaFichasProducao = new ArrayList<>();
			this.listaMaquinas = new ArrayList<>();
			this.listaOrdensProducao = new ArrayList<>();
			this.listaNotificacoesErro = new ArrayList<>();
			this.listaMensagens = new ArrayList<>();
			this.listaMovimentosStock = new ArrayList<>();
			this.listaAgendamentosDeProcessamento = new ArrayList<>();
		}

		Builder loadLinhasProducao() {
			LinhaProducaoRepository lProdRepo = repoFact.linhasProducao();
			this.listaLinhaProd = loadList(lProdRepo.findAll());
			verifyListIsNotEmpty(listaLinhaProd);
			return this;
		}

		Builder loadDepositos() {
			DepositoRepository depoRepo = repoFact.depositos();
			this.listaDepositos = loadList(depoRepo.findAll());
			verifyListIsNotEmpty(listaDepositos);
			return this;
		}

		Builder loadCategorias() {
			CategoriaRepository catRepo = repoFact.categoria();
			this.listaCategoria = loadList(catRepo.findAll());
			verifyListIsNotEmpty(listaCategoria);
			return this;
		}

		Builder loadProdutos() {
			ProdutoRepository prodRepo = repoFact.produto();
			this.listaProdutos = loadList(prodRepo.findAll());
			verifyListIsNotEmpty(listaProdutos);
			return this;
		}

		Builder loadMateriais() {
			MaterialRepository matRepo = repoFact.material();
			this.listaMateriais = loadList(matRepo.findAll());
			verifyListIsNotEmpty(listaMateriais);
			return this;
		}

		Builder loadFichasProducao() {
			FichaDeProducaoRepository fichaProdRepo = repoFact.fichaDeProducao();
			this.listaFichasProducao = loadList(fichaProdRepo.findAll());
			verifyListIsNotEmpty(listaFichasProducao);
			return this;
		}

		Builder loadMaquinas() {
			MaquinaRepository maqRepo = repoFact.maquinas();
			this.listaMaquinas = loadList(maqRepo.findAll());
			verifyListIsNotEmpty(listaMaquinas);
			return this;
		}

		Builder loadOrdensProducao(Date dataAFiltrar, boolean carregarTemposProducao) {
			OrdemProducaoRepository ordProdRepo = repoFact.ordemProducao();
			this.listaOrdensProducao = ordProdRepo.findAllWithDateAfter(dataAFiltrar);
			verifyListIsNotEmpty(listaOrdensProducao);
			for(OrdemProducao ordemProducao : listaOrdensProducao) {
				ordemProducao.carregarTemposProducao(carregarTemposProducao);
			}
			return this;
		}

		Builder loadMovimentosStock() {
			MovimentoStockRepository movStockRepo = repoFact.movimentoStock();
			this.listaMovimentosStock = loadList(movStockRepo.findAll());
			verifyListIsNotEmpty(listaMovimentosStock);
			return this;
		}

		Builder loadDesvios(Date dataAFiltrar) {
			throw new UnsupportedOperationException("Desvios não foram implementados");
		}

		Builder loadNotificacaoesErros() {
			NotificacaoErroRepository notifErroRepo = repoFact.notificacoesErros();
			this.listaNotificacoesErro = loadList(notifErroRepo.findAll());
			verifyListIsNotEmpty(listaNotificacoesErro);
			return this;
		}

		Builder loadMensagens(Date dataAFiltrar) {
			MensagemRepository msgRepo = repoFact.mensagem();
			this.listaMensagens = msgRepo.findAllWithDateAfter(dataAFiltrar);
			verifyListIsNotEmpty(listaMensagens);
			return this;
		}

		Builder loadAgendamentosProcessamento(Date dataAFiltrar) {
			AgendamentoDeProcessamentoRepository agenRepo = repoFact.agendamentoDeProcessamento();
			this.listaAgendamentosDeProcessamento = agenRepo.findAllWithDateAfter(dataAFiltrar);
			verifyListIsNotEmpty(listaMensagens);
			return this;
		}

		/**
		 * Verifica se uma lista está vazia, e se estiver
		 * faz prepara tudo
		 *
		 * @param list a lista que pretendemos verificar
		 * @param <E>  o objeto de dominio da lista
		 * @return verdadeiro se a lista não estiver fazia <br>
		 * falso se a lista estiver vazia
		 */
		private <E> boolean verifyListIsNotEmpty(List<E> list) {
			if (list.isEmpty()) {
				return false;
			} else {
				hasLoadedNothing = false;
				return true;
			}
		}

		/**
		 * Converte de um iterable(retornado pelo repositório) numa lista
		 *
		 * @param iterable o iteravel retornado do repositório
		 * @param <E>      o elemento da lista
		 * @return uma lista de elementos
		 */
		private <E> List<E> loadList(Iterable<E> iterable) {
			return Lists.newArrayList(iterable);
		}

		public ChaoDeFabrica build() {
			return new ChaoDeFabrica(hasLoadedNothing, listaLinhaProd, listaDepositos,
					listaCategoria, listaProdutos, listaMateriais, listaFichasProducao, listaMaquinas,
					listaOrdensProducao, listaNotificacoesErro, listaMensagens, listaMovimentosStock,
					listaAgendamentosDeProcessamento);
		}

	}
}
