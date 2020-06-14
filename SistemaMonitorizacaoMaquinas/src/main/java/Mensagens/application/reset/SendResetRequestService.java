package Mensagens.application.reset;

import ChaoDeFabrica.domain.Maquina.Maquina;
import Mensagens.application.Port;
import Mensagens.application.ReceiveAcknowledgmentService;
import Mensagens.domain.BroadcastAcknowledge;
import Mensagens.domain.ResetRequest;
import Mensagens.domain.Version;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Arrays;

public class SendResetRequestService implements Runnable {
	private static final int TIMEOUT = 5000;
	private Maquina maquina;
	private int idLinhaProducao;

	public SendResetRequestService(Maquina maquina, int idLinhaProducao) {
		this.maquina = maquina;
		this.idLinhaProducao = idLinhaProducao;
	}

	@Override
	public void run() {
		Version version = new Version(0);
		ResetRequest resetRequest = new ResetRequest(version, maquina, idLinhaProducao, Port.getSMMPort());
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket();
		} catch (IOException e) {
			System.out.println("Falha a criar o socket do datagram");
		}
		assert socket != null;

		System.out.println("A enviar pedido de reset á máquina " + maquina.toString() + " na linha de produção " +
				idLinhaProducao);

		try {
			resetRequest.getUdpPacket().setLength(ResetRequest.tamanhoMaxResetRequest());
			socket.send(resetRequest.getUdpPacket());
		} catch (IOException e) {
			System.out.println("Falha a enviar o pedido de reinicialização");
		}

		try {
			resetRequest.getUdpPacket().setLength(BroadcastAcknowledge.tamanhoMaxBroadcastAcknowledge());
			socket.setSoTimeout(TIMEOUT);
			socket.receive(resetRequest.getUdpPacket());
			resetRequest.getUdpPacket().setLength(resetRequest.getUdpPacket().getLength());
			System.out.println(resetRequest.getIdMaquina().value() + " " + resetRequest.getCodigo() + " || " + Arrays.toString(resetRequest.getUdpPacket().getData()));
			//Começa o serviço que está encarregue de receber respostas da máquina
			new ReceiveAcknowledgmentService(resetRequest.getUdpPacket()).run();
		} catch (IOException e) {
			System.out.println("Timeout á espera da resposta da máquina " + maquina.identity().value() +
					"para o pedido de reinicialização");
//			System.out.println("Erro de I/O a receber a resposta da máquina");
		}
	}
}
