package Mensagens.application.reset;

import ChaoDeFabrica.domain.Maquina.Maquina;
import Mensagens.application.Port;
import Mensagens.application.ReceiveAcknowledgmentService;
import Mensagens.domain.ResetRequest;
import Mensagens.domain.Version;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SendResetRequestService implements Runnable {
	private static final int TIMEOUT = 100;
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
			socket.send(resetRequest.getUdpPacket());
		} catch (IOException e) {
			System.out.println("Falha a enviar o pedido de reinicialização");
		}

		try {
			socket.setSoTimeout(TIMEOUT);
			socket.receive(resetRequest.getUdpPacket());
			//Começa o serviço que está encarregue de receber respostas da máquina
			new ReceiveAcknowledgmentService(resetRequest.getUdpPacket()).run();
		} catch (SocketException e) {
			System.out.println("Timeout á espera da resposta da máquina" + maquina.identity().toString() +
					"para o pedido de reinicialização");
		} catch (IOException e) {
//			System.out.println("Erro de I/O a receber a resposta da máquina");
		}
	}
}
