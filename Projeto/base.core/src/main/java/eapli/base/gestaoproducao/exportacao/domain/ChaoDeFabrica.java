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
import eapli.base.infrastructure.persistence.PersistenceContext;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "chaoDeFabrica")
public class ChaoDeFabrica {
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

	ChaoDeFabrica() {
		emptyLists = 7;
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
		return verifyAllIsLoaded();
	}

	/**
	 * Verifica que todas as listas foram carregadas
	 * @return
	 */
	public boolean verifyAllIsLoaded() {
		return emptyLists == 0;
	}

    private boolean loadLinhasProducao() {
		LinhaProducaoRepository lProdRepo = PersistenceContext.repositories().linhasProducao();
		this.listaLinhaProd = lProdRepo.findAllList();
		emptyLists--;
		return !listaLinhaProd.isEmpty();
	}

	private boolean loadDepositos() {
		DepositoRepository depoRepo = PersistenceContext.repositories().depositos();
		this.listaDepositos = depoRepo.findAllList();
		emptyLists--;
		return !listaDepositos.isEmpty();
	}

	private boolean loadCategorias() {
		CategoriaRepository catRepo = PersistenceContext.repositories().categoria();
		this.listaCategoria = catRepo.findAllList();
		emptyLists--;
		return !listaCategoria.isEmpty();
	}

	private boolean loadProdutos() {
		ProdutoRepository prodRepo = PersistenceContext.repositories().produto();
		this.listaProdutos = prodRepo.findAllList();
		emptyLists--;
		return !listaProdutos.isEmpty();
	}

	private boolean loadMateriais() {
		MaterialRepository matRepo = PersistenceContext.repositories().material();
		this.listaMateriais = matRepo.findAllList();
		emptyLists--;
		return !listaMateriais.isEmpty();
	}

	private boolean loadFichasProducao() {
		FichaDeProducaoRepository fichaProdRepo = PersistenceContext.repositories().fichaDeProducao();
		this.listaFichasProducao = fichaProdRepo.findAllList();
		emptyLists--;
		return !listaFichasProducao.isEmpty();
	}

	private boolean loadMaquinas() {
		MaquinaRepository maqRepo = PersistenceContext.repositories().maquinas();
		this.listaMaquinas = maqRepo.findAllList();
		emptyLists--;
		return !listaMaquinas.isEmpty();
	}
}
