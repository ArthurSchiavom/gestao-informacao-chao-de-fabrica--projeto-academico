package eapli.base.gestaoproducao.exportacao.domain;

import eapli.base.gestaoproducao.gestaodeposito.domain.Deposito;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaomaquina.domain.*;
import eapli.base.gestaoproducao.gestaomaterial.domain.*;
import eapli.base.gestaoproducao.gestaoproduto.application.ProdutoBuilder;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.medicao.UnidadeDeMedida;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ExportadorXMLStrategyTest {

	private final ExportadorXMLStrategy exportador = new ExportadorXMLStrategy();

	@Test
	public void garantirQueNaoSePodeExportarUmChaoDeVazioNulo() {
		assertFalse(exportador.export("teste", null));
	}

	@Test
	public void garantirQueNaoSePodeExportarBaseDeDadosVazia() {
		ChaoDeFabrica chaoDeFabrica = new ChaoDeFabrica();
		assertFalse(exportador.export("teste", chaoDeFabrica));
	}

	//TODO tirar isto daqui e meter num teste(vou ter que fazer mocks ugh)
//	public ChaoDeFabrica(int i) {
//		listaLinhaProd = new ArrayList<>();
//		LinhaProducao linhaProd = new LinhaProducao("LINHAPROD_1");
//		listaLinhaProd.add(new LinhaProducao("LINHAPROD_1"));
//
//		listaDepositos = new ArrayList<>();
//		listaDepositos.add(new Deposito("COD1", "Deposito Teste"));
//
//		listaCategoria = new ArrayList<>();
//		Categoria cat = new Categoria(new CodigoAlfanumericoCategoria("CAT1"), "Categoria Teste");
//		listaCategoria.add(cat);
//
//		listaProdutos = new ArrayList<>();
//		ProdutoBuilder prodBuilder = new ProdutoBuilder();
//		Produto prod = null;
//		try {
//			prodBuilder.setCategoriaDeProduto("CAT1");
//			prodBuilder.setDescricaoBreve("Descricao Teste Breve");
//			prodBuilder.setDescricaoCompleta("Descricao Teste Completa");
//			prodBuilder.setUnidadeDeMedida("kilogramas");
//			prodBuilder.setCodigoUnico("COD1", null);
//			prodBuilder.setCodigoComercial("CODCOM1", null);
//			prod = prodBuilder.build();
//		} catch (IllegalDomainValueException e) {
//			e.printStackTrace();
//		}
//		assert(prod != null);
//		listaProdutos.add(prod);
//
//		listaMateriais = new ArrayList<>();
//		try {
//			Material material = new Material("Descricao Material Teste", new CodigoInternoMaterial("COD_INT"), cat.codigoAlfanumericoCategoria,
//					UnidadeDeMedida.KILOGRAMA, new FichaTecnicaPDF(".", "PDF", "Conteudo teste"));
//			listaMateriais.add(material);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		listaFichasProducao = new ArrayList<>();
//		listaFichasProducao.add(new FichaDeProducao());
//
//		try {
//			NumeroSerie.definirRegrasNumeroSerie("10","1");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		listaMaquinas = new ArrayList<>();
//		listaMaquinas.add(new Maquina(new NumeroSerie("123"), new CodigoInternoMaquina("CODINT_1"),
//				new OrdemLinhaProducao(1), new IdentificadorProtocoloComunicacao("PROTOCOLO_ID"),
//				"Maquina teste", "Rage Against The Computer", "XPTO", linhaProd.identifier));
//	}
}