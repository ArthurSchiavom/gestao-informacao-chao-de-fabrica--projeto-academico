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
import eapli.base.infrastructure.persistence.PersistenceContext;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "chaoDeFabrica")
public class ChaoDeFabrica {
	private final int NUMBER_OF_LISTS = 8;
	private int emptyLists;

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

	ChaoDeFabrica() {
		emptyLists = NUMBER_OF_LISTS;
	}

	/**
	 * Carrega todos os dados do chão de fábrica
	 * @return verdadeiro se tudo o que carregou foi carregado com sucesso <br>
	 *     falso se carregou alguma lista vazia
	 */
	public boolean loadAllData() {
		loadLinhasProducao();
		loadDepositos();
		loadCategorias();
		loadProdutos();
		loadMateriais();
		loadFichasProducao();
		loadMaquinas();
		loadOrdensProducao();
		return verifyThisIsNotEmpty();
	}

	/**
	 * Verifica que pelo menos alguma coisa foi carregada
	 * @return true se pelo menos uma coisa foi carregada
	 */
	public boolean verifyThisIsEmpty() {
		return emptyLists == NUMBER_OF_LISTS;
	}

	/**
	 * Verifica se o chao de fabrica carregou alguma coisa
	 * @return verdadeiro se carregou <br> falso se nada foi carregado
	 */
	public boolean verifyThisIsNotEmpty() {
		return !verifyThisIsEmpty();
	}

    private boolean loadLinhasProducao() {
		LinhaProducaoRepository lProdRepo = PersistenceContext.repositories().linhasProducao();
		this.listaLinhaProd = lProdRepo.findAllList();
		return verifyListIsNotEmpty(listaLinhaProd);
	}

	private boolean loadDepositos() {
		DepositoRepository depoRepo = PersistenceContext.repositories().depositos();
		this.listaDepositos = depoRepo.findAllList();
		return verifyListIsNotEmpty(listaDepositos);
	}

	private boolean loadCategorias() {
		CategoriaRepository catRepo = PersistenceContext.repositories().categoria();
		this.listaCategoria = catRepo.findAllList();
		return verifyListIsNotEmpty(listaCategoria);
	}

	private boolean loadProdutos() {
		ProdutoRepository prodRepo = PersistenceContext.repositories().produto();
		this.listaProdutos = prodRepo.findAllList();
		return verifyListIsNotEmpty(listaProdutos);
	}

	private boolean loadMateriais() {
		MaterialRepository matRepo = PersistenceContext.repositories().material();
		this.listaMateriais = matRepo.findAllList();
		return verifyListIsNotEmpty(listaMateriais);
	}

	private boolean loadFichasProducao() {
		FichaDeProducaoRepository fichaProdRepo = PersistenceContext.repositories().fichaDeProducao();
		this.listaFichasProducao = fichaProdRepo.findAllList();
		return verifyListIsNotEmpty(listaFichasProducao);
	}

	private boolean loadMaquinas() {
		MaquinaRepository maqRepo = PersistenceContext.repositories().maquinas();
		this.listaMaquinas = maqRepo.findAllList();
		return verifyListIsNotEmpty(listaMaquinas);
	}

	private boolean loadOrdensProducao() {
		OrdemProducaoRepository ordProdRepo = PersistenceContext.repositories().ordemProducao();
		this.listaOrdensProducao = ordProdRepo.findAllList();
		return verifyListIsNotEmpty(listaOrdensProducao);
	}

	/**
	 * Verifica se uma lista está vazia, e se estiver
	 * faz prepara tudo
	 * @param lista a lista que pretendemos verificar
	 * @param <E> o objeto de dominio da lista
	 * @return verdadeiro se a lista não estiver fazia <br>
	 *     falso se a lista estiver vazia
	 */
	private <E> boolean verifyListIsNotEmpty(List<E> lista) {
		if(lista.isEmpty()) {
			return false;
		} else {
			emptyLists--;
			return true;
		}
	}
}
