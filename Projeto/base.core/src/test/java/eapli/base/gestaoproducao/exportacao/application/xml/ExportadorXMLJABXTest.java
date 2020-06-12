package eapli.base.gestaoproducao.exportacao.application.xml;

import eapli.base.comum.domain.medicao.QuantidadePositiva;
import eapli.base.comum.domain.medicao.UnidadeDeMedida;
import eapli.base.gestaoproducao.exportacao.domain.ChaoDeFabrica;
import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaodeposito.domain.Deposito;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.*;
import eapli.base.gestaoproducao.gestaomaterial.domain.*;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.MateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.TipoDeMateriaPrima;
import eapli.base.gestaoproducao.gestaomensagens.domain.*;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.application.ProdutoBuilder;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.*;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class ExportadorXMLJABXTest {
	private final ExportadorXMLJABX exportador = new ExportadorXMLJABX();

	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();
	MensagemID mid = new MensagemID(TipoDeMensagem.CONSUMO,new TimestampEmissao(new Date()),new CodigoInternoMaquina("123"));

	private static final Optional<Produto> optionalIsPresent = Optional.of(Mockito.mock(Produto.class));
	private static final Optional<Produto> optionalIsNotPresent = Optional.ofNullable(null);
	private static final ProdutoRepository produtoRepositoryIsPresent = Mockito.mock(ProdutoRepository.class);
	private static final ProdutoRepository produtoRepositoryIsNotPresent = Mockito.mock(ProdutoRepository.class);

	@BeforeClass
	public static void startup() {
		Mockito.when(produtoRepositoryIsPresent.produtoDeCodigoUnico(any())).thenReturn(optionalIsPresent);
		Mockito.when(produtoRepositoryIsNotPresent.produtoDeCodigoUnico(any())).thenReturn(optionalIsNotPresent);
	}

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

		List<QuantidadeDeMateriaPrima> listaQuantidadeMateriaPrima = new ArrayList<>();
		try {
			QuantidadeDeMateriaPrima quantidadeMateriaPrima = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(2), MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "2"));
			listaQuantidadeMateriaPrima.add(quantidadeMateriaPrima);
		} catch (IllegalDomainValueException e) {
			e.printStackTrace();
		}
		List<FichaDeProducao> listaFichasProducao = new ArrayList<>();
		try {
			listaFichasProducao.add(FichaDeProducao.valueOf(listaQuantidadeMateriaPrima));
		} catch (IllegalDomainValueException e) {
			e.printStackTrace();
		}

		try {
			NumeroSerie.definirRegrasNumeroSerie("10", "1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Maquina> listaMaquinas = new ArrayList<>();
		CodigoInternoMaquina codIntMaq = new CodigoInternoMaquina("CODINT_1");
		listaMaquinas.add(new Maquina(new NumeroSerie("123"), codIntMaq,
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
		try {
			listaOrdensProducao.add(new OrdemProducao(new IdentificadorOrdemProducao("ORDEM1"), new QuantidadeAProduzir(1550),
					listaIdentificadoresEncomenda, dateEmissao, datePrevEx, Estado.CONCLUIDA, CodigoUnico.valueOf("COD1", produtoRepositoryIsNotPresent)));
		} catch (IllegalDomainValueException e) {
			e.printStackTrace();
		}

		File ficheiro = null;
		try {
			ficheiro = folder.newFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert ficheiro != null;

		LinhaProducaoRepository lProdRepo = Mockito.mock(LinhaProducaoRepository.class);
		MensagemRepository msgRepo = Mockito.mock(MensagemRepository.class);
		Mockito.when(lProdRepo.containsOfIdentity(new IdentificadorLinhaProducao("LINHAPROD_1"))).thenReturn(true);
		Mockito.when(msgRepo.containsOfIdentity(mid)).thenReturn(true);

		List<NotificacaoErro> listaNotificacoesErro = new ArrayList<>();
		NotificacaoErro notifErro = new NotificacaoErro(new IdentificadorLinhaProducao("LINHAPROD_1"),
				TipoErroNotificacao.DADOS_INVALIDOS, mid, lProdRepo, msgRepo);
		listaNotificacoesErro.add(notifErro);

		List<Mensagem> listaMensagens = new ArrayList<>();
		try {
			MensagemConsumo msgConsumo = new MensagemConsumo(new CodigoDeposito("COD1"), codIntMaq, new Date(),
					10, CodigoUnico.valueOf("COD1", produtoRepositoryIsNotPresent));
		} catch (IllegalDomainValueException e) {
			e.printStackTrace();
		}

		ChaoDeFabrica chaoDeFabrica = new ChaoDeFabrica(false, listaLinhaProd, listaDepositos,
				listaCategoria, listaProdutos, listaMateriais, listaFichasProducao, listaMaquinas, listaOrdensProducao,
				listaNotificacoesErro, listaMensagens);
		assertTrue(exportador.export(ficheiro, chaoDeFabrica));
		exportador.export(new File("ola"), chaoDeFabrica);

		JAXBContext jaxbContext;
		try
		{
			//Get JAXBContext
			jaxbContext = JAXBContext.newInstance(ChaoDeFabrica.class);

			//Create Unmarshaller
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			//Setup schema validator
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema employeeSchema = sf.newSchema(new File("../../XSD/chaoDeFabrica.xsd"));
			jaxbUnmarshaller.setSchema(employeeSchema);

			//Unmarshal xml file
			ChaoDeFabrica chaoDeFabrica1 = (ChaoDeFabrica) jaxbUnmarshaller.unmarshal(ficheiro);
			assertNotNull(chaoDeFabrica1);
		}
		catch (JAXBException | SAXException e)
		{
			e.printStackTrace();
			fail("XSD FALHOU");
		}
	}
}