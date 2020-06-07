package ChaoDeFabrica.domain.Maquina;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MaquinaTest {
	private InetAddress ip;

	public MaquinaTest() {
		try {
			ip = InetAddress.getByName("100.100.100.100");
		} catch (UnknownHostException e) {
			fail("Criação do ip falhou, isto não devia acontecer");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueMaquinaTemIdMaquina() {
		new Maquina(null, EstadoMaquina.SUSPENSO, ip);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirqueMaquinaTemEstado() {
		new Maquina(new IdMaquina(2), null, ip);
	}

	@Test
	public void atualizarEstado() {
		Maquina maquina = new Maquina(new IdMaquina(2), EstadoMaquina.SUSPENSO, ip);
		maquina.atualizarEstado(EstadoMaquina.ATIVO);
		assertEquals(maquina.verificarEstado(), EstadoMaquina.ATIVO);
		maquina.atualizarEstado(EstadoMaquina.SUSPENSO);
		assertEquals(maquina.verificarEstado(), EstadoMaquina.SUSPENSO);
	}
}