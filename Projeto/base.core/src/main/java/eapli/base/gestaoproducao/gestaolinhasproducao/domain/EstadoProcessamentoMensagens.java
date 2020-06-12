package eapli.base.gestaoproducao.gestaolinhasproducao.domain;

public enum EstadoProcessamentoMensagens {
	ATIVO, SUSPENSO;

	public static boolean podeEfetuarReprocessamento(EstadoProcessamentoMensagens estadoPretendido) {
		return estadoPretendido == SUSPENSO;
	}
}
