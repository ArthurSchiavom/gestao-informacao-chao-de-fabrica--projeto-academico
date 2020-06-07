package Mensagens.domain;

import ChaoDeFabrica.domain.Maquina.Maquina;

public class ResetRequest extends MensagemUDP {
	public ResetRequest(Version version, Maquina maquina, int idLinhaProducao, int port) {
		super(version, Codigos.RESET, maquina.identity(), new MessageData(new RawData(String.valueOf(idLinhaProducao))),
				maquina.address(), port);
	}
}
