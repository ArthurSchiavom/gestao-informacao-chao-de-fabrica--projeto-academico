package LinhaProducao.domain.Maquina;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaquinaTest {
	@Test(expected = IllegalArgumentException.class)
	public void garantirQueMaquinaTemIdMaquina() {
		new Maquina(null, EstadoMaquina.SUSPENSO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirqueMaquinaTemEstado() {
		new Maquina(new IdMaquina(2), null);
	}

	@Test
	public void atualizarEstado() {
		Maquina maquina = new Maquina(new IdMaquina(2), EstadoMaquina.SUSPENSO);
		maquina.atualizarEstado(EstadoMaquina.ATIVO);
		assertEquals(maquina.verificarEstado(), EstadoMaquina.ATIVO);
		maquina.atualizarEstado(EstadoMaquina.SUSPENSO);
		assertEquals(maquina.verificarEstado(), EstadoMaquina.SUSPENSO);
	}
}