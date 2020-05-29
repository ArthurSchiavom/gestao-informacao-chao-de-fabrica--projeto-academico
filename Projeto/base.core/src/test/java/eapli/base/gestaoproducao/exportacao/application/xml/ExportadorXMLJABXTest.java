package eapli.base.gestaoproducao.exportacao.application.xml;

import eapli.base.gestaoproducao.exportacao.domain.ChaoDeFabrica;
import eapli.base.gestaoproducao.gestaodeposito.domain.Deposito;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaomaquina.domain.*;
import eapli.base.gestaoproducao.gestaomaterial.domain.*;
import eapli.base.gestaoproducao.gestaoproduto.application.ProdutoBuilder;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.medicao.UnidadeDeMedida;
import eapli.base.gestaoproducao.ordemProducao.domain.*;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ExportadorXMLJABXTest {
	private final ExportadorXMLJABX exportador = new ExportadorXMLJABX();

	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueNaoSePodeExportarUmChaoDeVazioNulo() {
		try {
			File teste = folder.newFile();
			exportador.export(teste, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueNaoSePodeExportarBaseDeDadosVazia() {
		RepositoryFactory repositoryFactory = Mockito.mock(RepositoryFactory.class);
		ChaoDeFabrica.Builder builder = new ChaoDeFabrica.Builder(repositoryFactory);
		ChaoDeFabrica chaoDeFabrica = builder.build();
		try {
			File teste = folder.newFile();
			exportador.export(teste, chaoDeFabrica);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void garantirQueExportacaoTemSucesso() {
		List<LinhaProducao> listaLinhaProd = new ArrayList<>();
		LinhaProducao linhaProd = new LinhaProducao("LINHAPROD_1");
		listaLinhaProd.add(new LinhaProducao("LINHAPROD_1"));

		List<Deposito> listaDepositos = new ArrayList<>();
		listaDepositos.add(new Deposito("COD1", "Deposito Teste"));

		List<Categoria> listaCategoria = new ArrayList<>();
		Categoria cat = new Categoria(new CodigoAlfanumericoCategoria("CAT1"), "Categoria Teste");
		listaCategoria.add(cat);

		List<Produto> listaProdutos = new ArrayList<>();
		ProdutoBuilder prodBuilder = new ProdutoBuilder();
		ProdutoRepository prodRepo = Mockito.mock(ProdutoRepository.class);

		Produto prod = null;
		try {
			prodBuilder.setCategoriaDeProduto("CAT1");
			prodBuilder.setDescricaoBreve("Descricao Teste Breve");
			prodBuilder.setDescricaoCompleta("Descricao Teste Completa");
			prodBuilder.setUnidadeDeMedida("kilogramas");
			prodBuilder.setCodigoUnico("COD1", prodRepo);
			prodBuilder.setCodigoComercial("CODCOM1", prodRepo);
			prod = prodBuilder.build();
		} catch (IllegalDomainValueException e) {
			e.printStackTrace();
		}
		assert (prod != null);
		listaProdutos.add(prod);

		List<Material> listaMateriais = new ArrayList<>();
		try {
			Material material = new Material("Descricao Material Teste",
					new CodigoInternoMaterial("COD_INT"), cat.codigoAlfanumericoCategoria,
					UnidadeDeMedida.KILOGRAMA, new FichaTecnicaPDF(".", "PDF",
					"Conteudo teste"));
			listaMateriais.add(material);
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<FichaDeProducao> listaFichasProducao = new ArrayList<>();
		listaFichasProducao.add(new FichaDeProducao());

		try {
			NumeroSerie.definirRegrasNumeroSerie("10", "1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Maquina> listaMaquinas = new ArrayList<>();
		listaMaquinas.add(new Maquina(new NumeroSerie("123"), new CodigoInternoMaquina("CODINT_1"),
				new OrdemLinhaProducao(1), new IdentificadorProtocoloComunicacao(1),
				"Maquina teste", "Rage Against The Computer", "XPTO", linhaProd));

		Date dateEmissao = null;
		Date datePrevEx = null;
		try {
			dateEmissao = new SimpleDateFormat("dd-MM-yyyy").parse("15-10-2013");
			datePrevEx = new SimpleDateFormat("dd-MM-yyyy").parse("15-10-2015");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assert dateEmissao != null;
		assert datePrevEx != null;

		List<IdentificadorEncomenda> listaIdentificadoresEncomenda = new ArrayList<>();
		listaIdentificadoresEncomenda.add(new IdentificadorEncomenda("ENCOMENDA1"));

		List<OrdemProducao> listaOrdensProducao = new ArrayList<>();
		listaOrdensProducao.add(new OrdemProducao(new Identificador("ORDEM1"), new QuantidadeAProduzir(1550),
				listaIdentificadoresEncomenda, dateEmissao, datePrevEx, Estado.CONCLUIDA));

		File ficheiro = null;
		try {
			ficheiro = folder.newFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert ficheiro != null;

		ChaoDeFabrica chaoDeFabrica = new ChaoDeFabrica(false, listaLinhaProd, listaDepositos,
				listaCategoria, listaProdutos, listaMateriais, listaFichasProducao, listaMaquinas, listaOrdensProducao, listaNotificacoesErro);
		assertTrue(exportador.export(ficheiro, chaoDeFabrica));
	}
}