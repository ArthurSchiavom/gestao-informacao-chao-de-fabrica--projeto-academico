package ChaoDeFabrica.domain;

import ChaoDeFabrica.domain.Maquina.IdMaquina;
import Mensagens.domain.Codigos;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class LinhaProducaoTest {
	private LinhaProducao linhaProducao = new LinhaProducao(2);
	private IdMaquina idMaquina = new IdMaquina(2);
	private InetAddress ip;

	public LinhaProducaoTest() {
		try {
			ip = InetAddress.getByName("100.100.100.100");
		} catch (UnknownHostException e) {
			fail("Criação do ip falhou");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void atualizarMaquinaComCodigoInvalido() {
		linhaProducao.adicionarMaquina(idMaquina, Codigos.ACK, ip);
		linhaProducao.atualizarMaquina(idMaquina, Codigos.HELLO, ip);
	}

	@Test(expected = IllegalArgumentException.class)
	public void adicionarMaquinaComCodigoInvalido() {
		linhaProducao.adicionarMaquina(idMaquina, Codigos.HELLO, ip);
	}

	@Test
	public void verificarQueMaquinaExiste() {
		linhaProducao.adicionarMaquina(idMaquina, Codigos.ACK, ip);
		assertTrue(linhaProducao.maquinaExiste(idMaquina));
		assertFalse(linhaProducao.maquinaExiste(new IdMaquina(3)));
	}

	@Test
	public void procurarPorMaquina() {
		linhaProducao.adicionarMaquina(idMaquina, Codigos.NACK, ip);
		assertNotNull(linhaProducao.procurarPorMaquina(idMaquina));
		assertNull(linhaProducao.procurarPorMaquina(new IdMaquina(3)));
	}
}