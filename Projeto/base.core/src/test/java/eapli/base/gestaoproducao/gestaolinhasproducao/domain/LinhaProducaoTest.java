package eapli.base.gestaoproducao.gestaolinhasproducao.domain;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class LinhaProducaoTest {
	@Test(expected = IllegalArgumentException.class)
	public void garantirIdentificadorNaoPodeSerNull() {
		new LinhaProducao(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirIdentificadorNaoPodeSerStringVazia() {
		new LinhaProducao("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirIdentificadorNaoPodeSerStringVaziaComEspacos() {
		new LinhaProducao(" ");
	}

	@Test
	public void garantirLinhaProducaoComIdentificador() {
		new LinhaProducao("Linha Produção 01");
		assertTrue(true);
	}

	@Test
	public void garantirNomeAtributoIdentidadeExiste() {
		boolean found = false;
		String identificador = LinhaProducao.identityAttributeName();
		for (Field field : LinhaProducao.class.getDeclaredFields()) {
			if (identificador.equals(field.getName()) &&
					field.getType().isAssignableFrom(IdentificadorLinhaProducao.class)) {
				found = true;
				break;
			}
		}
		if (found) {
			assertTrue(true);
		} else {
			fail("Verificar valor de retorno do metodo identityAttributeName");
		}
	}

	@Test
	public void garantirQueNaoSePodeAlterarEstadosDeProcessamentoParaOEstadoAtual() {
		LinhaProducao lProd = new LinhaProducao("teste");
		assertFalse(lProd.podeMudarParaEstado(EstadoProcessamentoMensagens.SUSPENSO));
	}

	@Test
	public void garantirQueSePodeMudarOEstadoParaUmEstadoDiferente() {
		LinhaProducao lProd = new LinhaProducao("teste");
		assertTrue(lProd.alterarEstado(EstadoProcessamentoMensagens.ATIVO));
	}

	@Test
	public void garantirQueNaoSePodeAlterarEstadosDeProcessamentoParaOEstadoAtualQuandoEleEstaAtivo() {
		LinhaProducao lProd = new LinhaProducao("teste");
		lProd.alterarEstado(EstadoProcessamentoMensagens.ATIVO);
		assertFalse(lProd.podeMudarParaEstado(EstadoProcessamentoMensagens.ATIVO));
	}

	@Test
	public void garantirQueAUltimaAtualizacaoDaDataFicaAlteradaQuandoOEstadoEAlterado() {
		LinhaProducao lProd = new LinhaProducao("teste");
		String firstData = lProd.obterUltimaVezAtualizado();
		try {
			Thread.sleep(1010);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("sleeping for one second failed somehow");
		}
		lProd.alterarEstado(EstadoProcessamentoMensagens.ATIVO);
		String secondData = lProd.obterUltimaVezAtualizado();
		assertNotEquals(firstData, secondData);
	}
}