package LinhaProducao.domain;

import LinhaProducao.domain.Maquina.IdMaquina;
import Mensagens.domain.Codigos;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinhaProducaoTest {
	private LinhaProducao linhaProducao = new LinhaProducao(2);
	private IdMaquina idMaquina = new IdMaquina(2);

	@Test(expected = IllegalArgumentException.class)
	public void atualizarMaquinaComCodigoInvalido() {
		linhaProducao.adicionarMaquina(idMaquina, Codigos.ACK);
		linhaProducao.atualizarMaquina(idMaquina, Codigos.HELLO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void adicionarMaquinaComCodigoInvalido() {
		linhaProducao.adicionarMaquina(idMaquina, Codigos.HELLO);
	}

	@Test
	public void verificarQueMaquinaExiste() {
		linhaProducao.adicionarMaquina(idMaquina, Codigos.ACK);
		assertTrue(linhaProducao.maquinaExiste(idMaquina));
		assertFalse(linhaProducao.maquinaExiste(new IdMaquina(3)));
	}

	@Test
	public void procurarPorMaquina() {
		linhaProducao.adicionarMaquina(idMaquina, Codigos.NACK);
		assertNotNull(linhaProducao.procurarPorMaquina(idMaquina));
		assertNull(linhaProducao.procurarPorMaquina(new IdMaquina(3)));
	}
}