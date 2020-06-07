package ChaoDeFabrica.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChaoDeFabricaTest {

	@Test
	public void verificarQueListaProducaoExiste() {
		ChaoDeFabrica chaoDeFabrica = ChaoDeFabrica.getInstance();
		chaoDeFabrica.adicionarLinhaProducao(2);
		assertFalse(chaoDeFabrica.verificarQueListaProducaoExiste(1));
		assertTrue(chaoDeFabrica.verificarQueListaProducaoExiste(2));
	}

	@Test
	public void procurarPorLinhaProducao() {
		ChaoDeFabrica chaoDeFabrica = ChaoDeFabrica.getInstance();
		chaoDeFabrica.adicionarLinhaProducao(2);
		assertNotNull(chaoDeFabrica.procurarPorLinhaProducao(2));
	}
}