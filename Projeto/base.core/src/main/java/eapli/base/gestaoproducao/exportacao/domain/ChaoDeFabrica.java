package eapli.base.gestaoproducao.exportacao.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.Deposito;
import eapli.base.gestaoproducao.gestaodeposito.repository.DepositoRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomaterial.repository.CategoriaRepository;
import eapli.base.gestaoproducao.gestaomaterial.repository.MaterialRepository;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.FichaDeProducaoRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.persistence.RepositoryFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
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

	private boolean nothingWasLoaded;

	public ChaoDeFabrica(boolean nothingWasLoaded, List<LinhaProducao> listaLinhaProd, List<Deposito> listaDepositos,
	                     List<Categoria> listaCategoria, List<Produto> listaProdutos,
	                     List<Material> listaMateriais, List<FichaDeProducao> listaFichasProducao,
	                     List<Maquina> listaMaquinas, List<OrdemProducao> listaOrdensProducao) {
		this.nothingWasLoaded = nothingWasLoaded;
		this.listaLinhaProd = listaLinhaProd;
		this.listaDepositos = listaDepositos;
		this.listaCategoria = listaCategoria;
		this.listaProdutos = listaProdutos;
		this.listaMateriais = listaMateriais;
		this.listaFichasProducao = listaFichasProducao;
		this.listaMaquinas = listaMaquinas;
		this.listaOrdensProducao = listaOrdensProducao;
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
		}

		public Builder loadLinhasProducao() {
			LinhaProducaoRepository lProdRepo = repoFact.linhasProducao();
			this.listaLinhaProd = lProdRepo.findAllList();
			verifyListIsNotEmpty(listaLinhaProd);
			return this;
		}

		public Builder loadDepositos() {
			DepositoRepository depoRepo = repoFact.depositos();
			this.listaDepositos = depoRepo.findAllList();
			verifyListIsNotEmpty(listaDepositos);
			return this;
		}

		public Builder loadCategorias() {
			CategoriaRepository catRepo = repoFact.categoria();
			this.listaCategoria = catRepo.findAllList();
			verifyListIsNotEmpty(listaCategoria);
			return this;
		}

		public Builder loadProdutos() {
			ProdutoRepository prodRepo = repoFact.produto();
			this.listaProdutos = prodRepo.findAllList();
			verifyListIsNotEmpty(listaProdutos);
			return this;
		}

		public Builder loadMateriais() {
			MaterialRepository matRepo = repoFact.material();
			this.listaMateriais = matRepo.findAllList();
			verifyListIsNotEmpty(listaMateriais);
			return this;
		}

		public Builder loadFichasProducao() {
			FichaDeProducaoRepository fichaProdRepo = repoFact.fichaDeProducao();
			this.listaFichasProducao = fichaProdRepo.findAllList();
			verifyListIsNotEmpty(listaFichasProducao);
			return this;
		}

		public Builder loadMaquinas() {
			MaquinaRepository maqRepo = repoFact.maquinas();
			this.listaMaquinas = maqRepo.findAllList();
			verifyListIsNotEmpty(listaMaquinas);
			return this;
		}

		public Builder loadOrdensProducao(Date dataAFiltrar) {
			OrdemProducaoRepository ordProdRepo = repoFact.ordemProducao();
			this.listaOrdensProducao = ordProdRepo.findAllwithDateAfter(dataAFiltrar);
			verifyListIsNotEmpty(listaOrdensProducao);
			return this;
		}

		public void loadTemposDeProducao(Date dataAFiltrar) {
			throw new UnsupportedOperationException("Tempos de Produção ainda não foram implementados");
		}

		public void loadDesvios(Date dataAFiltrar) {
			throw new UnsupportedOperationException("Desvios não foram implementados");
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

		public ChaoDeFabrica build() {
			return new ChaoDeFabrica(hasLoadedNothing, listaLinhaProd, listaDepositos,
					listaCategoria, listaProdutos, listaMateriais, listaFichasProducao, listaMaquinas,
					listaOrdensProducao);
		}

	}
}
